package org.flight_helper.flight_helper_bigdata.ml;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.StandardScaler;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;


import java.io.IOException;

import static org.apache.spark.sql.functions.*;


public class FlightPricePrediction {
    public static void main(String[] args) throws IOException {

        Logger.getLogger("org.apache.spark").setLevel(Level.INFO);

        long currentTimestamp = System.currentTimeMillis();
        String SAVE_PATH = "file:/Users/xuezhihengg/Projects/plane_helper_bigdata/src/main/resources/ml_outputs/";

        SparkSession sparkSession = SparkSession.builder()
                .appName("FlightPricePrediction")
                .master("spark://192.168.100.184:7077")
                .config("spark.sql.warehouse.dir", "hdfs://192.168.100.184:9000/user/hive/warehouse")
                .config("hive.metastore.uris", "thrift://192.168.100.184:9083")
                .config("spark.executor.memory", "1g")  // 限制每个 Executor 1G 内存
                .config("spark.executor.cores", "1")    // 每个 Executor 1 核
                .config("spark.driver.memory", "2g")    // 限制 Driver 2G 内存
                .config("spark.driver.host", "192.168.100.81") // important！ 要指定客户端的IP也就是运行这个程序的机器
                .config("spark.executor.instances", "2") // 限制 Executor 数量
                .enableHiveSupport()
                .getOrCreate();

        sparkSession.sparkContext().setLogLevel("INFO");
        // 读取 Hive 数据
        sparkSession.sql("USE fly");

        // 执行查询，筛选出指定时间段的数据
        String query = "SELECT " +
                "searchdate AS search_date, " +
                "flightdate AS flight_date, " +
                "startingairport AS starting_airport, " +
                "destinationairport AS destination_airport," +
                "totalfare AS total_fare " +
                "FROM ods";

        // 执行Spark SQL查询
        Dataset<Row> df = sparkSession.sql(query);

        df = df.withColumn("flight_date_timestamp", functions.unix_timestamp(functions.col("flight_date"), "yyyy-MM-dd"))
                .withColumn("days_before_departure",
                datediff(col("flight_date"), col("search_date")));

        StringIndexer staringIndexer = new StringIndexer()
                .setInputCol("starting_airport")
                .setOutputCol("starting_index");

        StringIndexer destinationIndexer = new StringIndexer()
                .setInputCol("destination_airport")
                .setOutputCol("destination_index");

        df = staringIndexer.fit(df).transform(df);
        df = destinationIndexer.fit(df).transform(df);

        df.show(100, false);

        // 将多个数值特征合并成一个特征向量features
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"starting_index", "destination_index", "days_before_departure", "flight_date_timestamp"})
                .setOutputCol("features")
                .setHandleInvalid("skip");

        StandardScaler scaler = new StandardScaler()
                .setInputCol("features")
                .setOutputCol("scaled_features")
                .setWithStd(true)  // 设置标准差为 1
                .setWithMean(true);  // 设置均值为 0

        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{assembler, scaler});

        PipelineModel pipModel = pipeline.fit(df);
        df = pipModel.transform(df);
        df = df.select("scaled_features", "total_fare")
                .dropDuplicates();  // 去重复行

        df.show(100, false);

        //TODO: bug here
//        df.limit(10000)
//                .withColumn("scaled_features_str", functions.col("scaled_features").cast("string"))
//                .select("scaled_features_str", "total_fare")
//                .write()
//                .option("header", "true")
//                .csv(SAVE_PATH + "scaled_features_" + currentTimestamp + ".csv");

        GBTRegressor gbtRegressor = new GBTRegressor()
                .setLabelCol("total_fare")
                .setFeaturesCol("scaled_features")
                .setMaxIter(100)  // 设置最大迭代次数
                .setMaxDepth(10)   // 设置树的最大深度
                .setStepSize(0.5)
                .setMinInstancesPerNode(2); // 每个节点的最小实例数


        // 划分训练集和测试集
        Dataset<Row>[] splits = df.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainData = splits[0];
        Dataset<Row> testData = splits[1];

        // 训练模型
        GBTRegressionModel model = gbtRegressor.fit(trainData);

        // 预测
        Dataset<Row> predictions = model.transform(testData);
        predictions.show(100, false);

        //TODO: bug here
//        predictions.select("total_fare", "prediction")
//                .write()
//                .option("header", "true")
//                .csv(SAVE_PATH + "predictions_" + currentTimestamp + ".csv");

        RegressionEvaluator evaluator = new RegressionEvaluator()
                .setLabelCol("total_fare")
                .setPredictionCol("prediction")
                .setMetricName("rmse");

        double rmse = evaluator.evaluate(predictions);
        for (int i=0; i< 100; i++) {
            System.out.println("RMSE: " + rmse);
        }

        model.save("src/main/resources/ml_outputs/GBTModel_" + currentTimestamp);
    }
}
