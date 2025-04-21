
//...机票搜索页面
// 机票列表对象
export interface Flight {
  id: string;
  airline: string;
  flightNumber: string;
  departure: string;
  arrival: string;
  departureTime: string;
  arrivalTime: string;
  duration: string;
  price: number;
  aircraft: string;
  stops: number;

}

// 机票搜索条件
export interface SearchCriteria{
  airlines: string[],
  priceRange: number[],
  departureTime: string,
  aircraftTypes: string[],
  departureCity: string,
  arrivalCity: string,
  departureDate: string | null,
}


//...智能组合页面 

//路线对象和价格
export interface Route {
  dept_city: string;
  arri_city: string;
  dept_pos: [number, number];
  arri_pos: [number, number];
  departureTime: string;
  arrivalTime: string;
  flightNumber: string;
  price: string;
}


// 完整路线（可能包含多个航段）
export interface CombinedRoute {
  segments: Route[];     // 航段数组
  totalPrice: number;            // 总价格
  totalDuration: number;         // 总飞行时间（分钟）
  transferCount: number;         // 中转次数
  transferDuration?: number;     // 总中转时间（分钟）
  isDirectFlight: boolean;       // 是否直飞
}

// 智能中转搜索条件

export interface SmartRoutingCriteria {
  departureCity: string;
  arrivalCity: string;
  departureDate: string;  
}


//...价格分析页面

//价格分析搜索条件

export interface PriceAnalysisCriteria {
  departureCity: string;//出发城市
  arrivalCity: string;//到达城市
  startDate: string;//开始日期
  endDate: string;//结束日期
}

// 何时飞搜索结果

export interface WhenToFlyData {
  priceData: PriceAnalysisData[];
  bestTravelDateData: BestTravelDateData;
  weeklyPriceTrendData: WeeklyPriceTrendData[];
}

//价格分析预测数据

export interface PriceAnalysisData {
  actualPrice: number;
  predictedPrice: number;
  date: string; // 假设还有一个日期字段
}

// 最佳出行日期数据

export interface BestTravelDateData {
  day: string;//星期几
  day_discount: string;//折扣
  time: string;//时间（凌晨，早上，下午，晚上）
  time_discount: string;//折扣
  saveprice: number;//节省价格
}

// 每周价格趋势

export interface WeeklyPriceTrendData {
  day: string;//星期几
  avgprice: number;//平均价格
}




//...航班趋势页面

//航班趋势搜索条件

export interface FlightTrendsCriteria {
  departureCity: string;
  arrivalCity: string;
  departureDate: string;
}


//航班热力图数据

export interface HeatmapData {
  day: string;
  hour: number;
  value: number;
}

//航班数量分布数据

export interface FlightCountDistribution {
  month: string;
  count: number;
}

// 航班时间分布

export interface FlightTimeDistribution {
  time: string;
  count: number;
}

//航班经停次数分布

export interface FlightStopCountDistribution {
  stopCount: number;
  count: number;
}

//...季节趋势页面

//季节趋势搜索条件

export interface SeasonTrendsCriteria {
  departureCity: string;
  arrivalCity: string;
}

//季节趋势数据

export interface SeasonTrendsData {
  trends: MonthTrendData[];
  peakSeasons: PeakSeasonData[];
  distribution: PeakSeasonDistribution[];
}


//月份趋势数据

export interface MonthTrendData {
  month: string;
  passengers: number;
  flights: number;
  avgPrice: number;
}

// 淡旺季数据

export interface PeakSeasonData {
  season: string;
  period: string;
  priceIncrease: string;
}

// 淡旺季分布

export interface PeakSeasonDistribution {
  type: string;
  value: number;
}


// ...排行榜页面


// 机场排行榜数据

export interface AirportRankingData {
  name: string;
  throughput: number;
}

// 城市排行榜数据

export interface CityRankingData {
  name: string;
  flights: number;
  airports: number;
}

// 航空公司排行榜数据

export interface AirlineRankingData {
  name: string;
  flights: number;
}

// app/utils/types.ts
export interface airportheatmapData {
  lat: number; // 纬度
  lng: number; // 经度
  flights: number; // 航班数
}