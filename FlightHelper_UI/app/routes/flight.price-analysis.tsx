import { Card, Row, Col,  DatePicker ,AutoComplete,Form, Alert, Tag, Typography, Button, Radio, Statistic, Tooltip } from 'antd';
import { Column} from '@ant-design/plots';
import { useState } from 'react';
import { RocketOutlined, ArrowUpOutlined, ArrowDownOutlined, InfoCircleOutlined, CalendarOutlined, SearchOutlined } from '@ant-design/icons';
import {
  VictoryChart,
  VictoryAxis,
  VictoryLine,
  VictoryScatter,
  VictoryTooltip,
  VictoryLegend,
  VictoryTheme,
} from 'victory';
import {airports} from 'public/assets/cities'
import { PriceAnalysisCriteria,PriceAnalysisData,BestTravelDateData,WeeklyPriceTrendData } from 'app/utils/types';
import type { Dayjs } from 'dayjs';
import { submitPriceAnalysisFilters } from 'app/utils/api';

export default function PriceAnalysis() {

  const [activeView, setActiveView] = useState('plots');
  const [criteria, setCriteria] = useState<PriceAnalysisCriteria>({
    departureCity: '',
    arrivalCity: '',
    startDate: '',
    endDate: '',
  });
  const [priceData, setPriceData] = useState<PriceAnalysisData[]>([]);
  const [bestTravelDateData, setBestTravelDateData] = useState<BestTravelDateData>({
    day: '',
    day_discount: '',
    time: '',
    time_discount: '',
    saveprice: 0
  });
  const [weeklyPriceTrendData, setWeeklyPriceTrendData] = useState<WeeklyPriceTrendData[]>([]);


  // 生成自动补全选项
  const options = airports.map(loc => ({
    value: `${loc.value}`, // 显示地点和三字码
  }));


  // 提交筛选条件到后端
  const handleSubmit = async () => {
    const data = await submitPriceAnalysisFilters(criteria);
    if (data.priceData.length > 0) {
      setPriceData(data.priceData); // 更新航班数据状态
      setBestTravelDateData(data.bestTravelDateData); // 更新航班数据状态
      setWeeklyPriceTrendData(data.WeeklyPriceTrendData); // 更新航班数据状态
      console.log(data.weeklyPriceTrendData); // 确保数据正确
    } else {
      setPriceData([]); // 如果格式不正确，设置为空数组
      setBestTravelDateData({
        day: '',
        day_discount: '',
        time: '',
        time_discount: '',
        saveprice: 0
      }); // 如果格式不正确，设置为空数组
      setWeeklyPriceTrendData([]); // 如果格式不正确，设置为空数组
    }
  };

  // 更新函数
  const handleDepartureChange = (value: string) => {
    setCriteria(prev => ({ ...prev, departureCity: value }));
  };

  const handleArrivalChange = (value: string) => {
    setCriteria(prev => ({ ...prev, arrivalCity: value }));
  };

  const handleDateChange = (
    dates: [Dayjs | null, Dayjs | null] | null,
    dateStrings: [string, string]
  ) => {
    if (dates && dates[0] && dates[1]) {
      setCriteria(prev => ({
        ...prev,
        startDate: dateStrings[0],
        endDate: dateStrings[1]
      }));
    }
  };

  // 添加分析函数
  const analyzePriceTrends = (data: typeof priceData) => {
    const highestPrice = Math.max(...data.map(item => item.predictedPrice));
    const lowestPrice = Math.min(...data.map(item => item.predictedPrice));
    const highestDate = data.find(item => item.predictedPrice === highestPrice)?.date;
    const lowestDate = data.find(item => item.predictedPrice === lowestPrice)?.date;

    return { highestPrice, lowestPrice, highestDate, lowestDate };
  };
  const PriceChart = ({ priceData }: { priceData: PriceAnalysisData[] }) => {
    return (
      <div style={{ width: '100%', height: 600 }}>
        <VictoryChart
          theme={VictoryTheme.material}
          width={800}
          height={500}
          domainPadding={20}
        >
          {/* X 轴 */}
          <VictoryAxis
            tickValues={priceData.map(item => item.date)}
            tickFormat={priceData.map(item => item.date)}
            style={{ tickLabels: { angle: -45, fontSize: 10 } }}
          />
          {/* Y 轴 */}
          <VictoryAxis dependentAxis />
  
          {/* 图例 */}
          <VictoryLegend
            x={50}
            y={10}
            orientation="horizontal"
            gutter={20}
            data={[
              { name: 'actualPrice', symbol: { fill: '#5B8FF9' } },
              { name: 'predictedPrice', symbol: { fill: '#F6BD16' } },
            ]}
          />
  
          {/* 实际价格折线 */}
          <VictoryLine
            data={priceData}
            x="date"
            y="actualPrice"
            style={{
              data: { stroke: '#5B8FF9', strokeWidth: 2 },
            }}
            animate={{ duration: 800, onLoad: { duration: 250 } }}
          />
          {/* 实际价格数据点 */}
          <VictoryScatter
            data={priceData}
            x="date"
            y="actualPrice"
            labels={({ datum }) => `actualPrice: ${datum.actualPrice}`}
            labelComponent={<VictoryTooltip />}
            size={4}
            style={{ data: { fill: '#5B8FF9' } }}
          />
  
          {/* 预测价格折线 */}
          <VictoryLine
            data={priceData}
            x="date"
            y="predictedPrice"
            style={{
              data: { stroke: '#F6BD16', strokeWidth: 2 },
            }}
            animate={{ duration: 800, onLoad: { duration: 250 } }}
          />
          {/* 预测价格数据点 */}
          <VictoryScatter
            data={priceData}
            x="date"
            y="predictedPrice"
            labels={({ datum }) => `predictedPrice: ${datum.predictedPrice}`}
            labelComponent={<VictoryTooltip />}
            size={4}
            style={{ data: { fill: '#F6BD16' } }}
          />
        </VictoryChart>
      </div>
    );
  };

  return (
    <div>
      <Card style={{ marginBottom: 24 }}>
        <Row gutter={16} align="middle">
          <Col span={7}>
            <Form.Item label="出发地">
              <AutoComplete
                options={options}
                onChange={handleDepartureChange}
                placeholder="请输入出发城市"
                prefix={<RocketOutlined rotate={-45} />}
              />
            </Form.Item>
          </Col>
          <Col span={7}>
            <Form.Item label="目的地">
              <AutoComplete
                options={options}
                onChange={handleArrivalChange}
                placeholder="请输入到达城市"
                prefix={<RocketOutlined rotate={45} />}
              />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item label="日期范围">
              <DatePicker.RangePicker 
                onChange={handleDateChange}
                format="YYYYMMDD"
              />
            </Form.Item>
          </Col>
          <Col span={2}>
            <Form.Item>
              <Button type="primary" icon={<SearchOutlined />} onClick={handleSubmit}>
                搜索
              </Button>
            </Form.Item>
          </Col>

          <Col>
            <Radio.Group 
              value={activeView} 
              onChange={e => setActiveView(e.target.value)}
              style={{ marginRight: 16 }}
            >
              <Radio.Button value="plots">区间价格预测</Radio.Button>
              <Radio.Button value="conclusion">最佳飞行时段</Radio.Button>
            </Radio.Group>
          </Col>
        </Row>
      </Card>

      {activeView === 'plots' && (
        <Row gutter={24}>
          <Col span={24}>
            <Card title="区间价格预测" style={{ marginBottom: 24 }}>
              <PriceChart priceData={priceData} />
              <div style={{ marginTop: 16 }}>
                <span style={{ marginRight: 16 }}>
                  <Tag color="#5B8FF9">实际价格</Tag>
                </span>
                <span>
                  <Tag color="#F6BD16">预测价格</Tag>
                </span>
              </div>

              {/* 分析结论区域 */}
              {priceData.length > 0 && (
                <div style={{ 
                  marginTop: 24, 
                  padding: '16px 24px',
                  background: '#f5f5f5',
                  borderRadius: '8px'
                }}>
                  <Row gutter={[24, 16]}>
                    <Col span={24}>
                      <Alert
                        message="价格走势分析"
                        description="以下分析基于历史数据预测，仅供参考。建议关注实时价格变化。"
                        type="info"
                        showIcon
                        style={{ marginBottom: 16 }}
                      />
                    </Col>
                    <Col span={12}>
                      <Card
                        size="small"
                        style={{ background: '#fff7e6', border: '1px solid #ffe7ba' }}
                      >
                        <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                          <ArrowUpOutlined style={{ color: '#fa541c', fontSize: 20 }} />
                          <div>
                            <div style={{ fontWeight: 'bold', marginBottom: 4 }}>预计最高价格</div>
                            <div>
                              <Tag color="orange">
                                <CalendarOutlined /> {analyzePriceTrends(priceData).highestDate}
                              </Tag>
                              <span style={{ marginLeft: 8, color: '#fa541c', fontWeight: 'bold' }}>
                                ¥{analyzePriceTrends(priceData).highestPrice}
                              </span>
                            </div>
                          </div>
                        </div>
                      </Card>
                    </Col>
                    <Col span={12}>
                      <Card
                        size="small"
                        style={{ background: '#e6fffb', border: '1px solid #b5f5ec' }}
                      >
                        <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                          <ArrowDownOutlined style={{ color: '#13c2c2', fontSize: 20 }} />
                          <div>
                            <div style={{ fontWeight: 'bold', marginBottom: 4 }}>预计最低价格</div>
                            <div>
                              <Tag color="cyan">
                                <CalendarOutlined /> {analyzePriceTrends(priceData).lowestDate}
                              </Tag>
                              <span style={{ marginLeft: 8, color: '#13c2c2', fontWeight: 'bold' }}>
                                ¥{analyzePriceTrends(priceData).lowestPrice}
                              </span>
                            </div>
                          </div>
                        </div>
                      </Card>
                    </Col>
                    <Col span={24}>
                      <div style={{ 
                        padding: '12px 16px', 
                        background: '#ffffff',
                        borderRadius: '4px',
                        border: '1px solid #d9d9d9'
                      }}>
                        <Typography.Text type="secondary">
                          <InfoCircleOutlined style={{ marginRight: 8 }} />
                          建议：{analyzePriceTrends(priceData).lowestPrice < priceData[0].predictedPrice ? 
                            '当前价格处于相对高位，建议等待价格回落后再购买。' : 
                            '当前价格相对较低，是较好的购买时机。'}
                        </Typography.Text>
                      </div>
                    </Col>
                  </Row>
                </div>
              )}
            </Card>
          </Col>
        </Row>
      )}

      {activeView === 'conclusion' && (
        <Row gutter={24}>
          <Col span={12}>
            <Card 
              title={
                <span>
                  最佳出行日 
                  <Tooltip title="基于历史数据分析的最优惠日期">
                    <InfoCircleOutlined style={{ marginLeft: 8 }} />
                  </Tooltip>
                </span>
              }
              style={{ marginBottom: 24 }}
            >
              <Row gutter={16}>
                <Col span={8}>
                  <Statistic 
                    title="最优惠日期" 
                    value={bestTravelDateData.day}
                    suffix={<small style={{ color: '#52c41a' }}> Avgsave：{bestTravelDateData.day_discount}</small>}
                  />
                </Col>
                <Col span={8}>
                  <Statistic 
                    title="最优惠时段" 
                    value={bestTravelDateData.time}
                    suffix={<small style={{ color: '#52c41a' }}> Avgsave：{bestTravelDateData.time_discount}</small>}
                  />
                </Col>
                <Col span={8}>
                  <Statistic 
                    title="平均节省" 
                    value={bestTravelDateData.saveprice}
                    valueStyle={{ color: '#52c41a' }}
                  />
                </Col>
              </Row>
            </Card>
          </Col>
          <Col span={12}>
          <Card title="每周价格趋势" style={{ marginBottom: 24 }}>
            <Column
              data={weeklyPriceTrendData}
              xField="day"
              yField="avgprice"
              label={{
                position: 'top',
                style: {
                  fill: '#666',
                }
              }}
              meta={{
                avgprice: {
                  alias: '平均价格',
                }
              }}
            />
          </Card>
        </Col>
        </Row>
      )}
    </div>
  );
} 