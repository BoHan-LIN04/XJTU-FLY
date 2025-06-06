import  {notification} from 'antd';


import  { SearchCriteria,SmartRoutingCriteria,PriceAnalysisCriteria,FlightTrendsCriteria,SeasonTrendsCriteria} from 'app/utils/types'


//...前后端通信api//


//实时查询机票接口
export const submitFilters = async (filters:SearchCriteria) => {
  try {
    const response = await fetch('http://127.0.0.1:4523/m1/5909167-5596115-default/flight/price-analysis', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(filters), // 将筛选条件作为请求体发送
    });

    if (!response.ok) {
      throw new Error('请求失败');
    }

    const result = await response.json(); // 获取完整的响应对象
    console.log('返回的数据:', result);

    // 检查返回的数据是否成功，并提取 data 数组
    if (result.success && Array.isArray(result.data)) {
      return result.data; // 返回获取的航班数据
    } else {
      console.error('返回的数据格式不正确:', result);
      return []; // 如果格式不正确，返回空数组
    }
  } catch (error) {
    console.error('请求错误:', error);
    notification.error({
      message: '网络错误',
      description: '请求失败，请检查网络连接或稍后重试。',
      placement: 'topRight', // 提示栏位置
    });
    return []; // 如果发生错误，返回空数组
  }
};

// 智能路线接口
export const submitRouteFilters = async (filters:SmartRoutingCriteria) => {
  try {
    const response = await fetch('http://127.0.0.1:4523/m1/5909167-5596115-default/api/flight/smart-routing', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(filters), // 将筛选条件作为请求体发送
    });

    if (!response.ok) {
      throw new Error('请求失败');
    }

    const result = await response.json(); // 获取完整的响应对象
    console.log('返回的数据:', result);

    // 检查返回的数据是否成功，并提取 data 数组
    if (result.success && Array.isArray(result.data)) {
      return result.data; // 返回获取的航班数据
    } else {
      console.error('返回的数据格式不正确:', result);
      return []; // 如果格式不正确，返回空数组
    }
  } catch (error) {
    console.error('请求错误:', error);
    notification.error({
      message: '网络错误',
      description: '请求失败，请检查网络连接或稍后重试。',
      placement: 'topRight', // 提示栏位置
    });
    return []; // 如果发生错误，返回空数组
  }
}



  //何时飞分析接口
  export const submitPriceAnalysisFilters = async (filters:PriceAnalysisCriteria) => {
    try {
      const response = await fetch('http://127.0.0.1:4523/m1/5909167-5596115-default/flight/price-analysis', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(filters), // 将筛选条件作为请求体发送
      });
  
      if (!response.ok) {
        throw new Error('请求失败');
      }
  
      const result = await response.json(); // 获取完整的响应对象
      console.log('返回的数据:', result);
  
      // 检查返回的数据是否成功，并提取 data 数组
      if (result.success) {
        return result.data; // 返回获取的航班数据
      } else {
        console.error('返回的数据格式不正确:', result);
        return {
          priceData: [],
          bestTravelDateData: {
            day: '',
            time: '',
            saveprice: 0
          },
          weeklyPriceTrendData: []
        }; // 如果格式不正确，返回空数组
      }
    } catch (error) {
      console.error('请求错误:', error);
      notification.error({
        message: '网络错误',
        description: '请求失败，请检查网络连接或稍后重试。',
        placement: 'topRight', // 提示栏位置
      });
       return {
        priceData: [],
        bestTravelDateData: {
          day: '',
          time: '',
          saveprice: 0
        },
        weeklyPriceTrendData: []
      }; // 如果发生错误，返回空数组
    }
  
}


//航班趋势

export const submitFlightTrendsFilters = async (filters:FlightTrendsCriteria) => {
  try {
    const response = await fetch('http://127.0.0.1:4523/m2/5909167-5596115-default/264485483', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(filters), // 将筛选条件作为请求体发送
    });

    if (!response.ok) {
      throw new Error('请求失败');
    }

    const result = await response.json(); // 获取完整的响应对象
    console.log('返回的数据:', result);

    // 检查返回的数据是否成功，并提取 data 数组
    if (result.success ) {
      return result.data; // 返回获取的航班数据
    } else {
      console.error('返回的数据格式不正确:', result);
      return {
        heatmapData: [],
        flightCountDistribution: [],
        flightTimeDistribution: [],
        flightStopCountDistribution: [],
      }; // 如果格式不正确，返回包含空数组的对象
    }
  } catch (error) {
    console.error('请求错误:', error);
    notification.error({
      message: '网络错误',
      description: '请求失败，请检查网络连接或稍后重试。',
      placement: 'topRight', // 提示栏位置
    });
    return {
      heatmapData: [],
      flightCountDistribution: [],
      flightTimeDistribution: [],
      flightStopCountDistribution: [],
    }; // 如果发生错误，返回包含空数组的对象
  }

}

//季节趋势
export const submitSeasonTrendsFilters = async (filters:SeasonTrendsCriteria) => {
  try {
    const response = await fetch('http://127.0.0.1:4523/m2/5909167-5596115-default/264730349', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(filters), // 将筛选条件作为请求体发送
    });

    if (!response.ok) {
      throw new Error('请求失败');
    }

    const result = await response.json(); // 获取完整的响应对象
    console.log('返回的数据:', result);

    // 检查返回的数据是否成功，并提取 data 数组
    if (result.success ) {
      return result.data; // 返回获取的航班数据
    } else {
      console.error('返回的数据格式不正确:', result);
      return {
        trends: [],
        peakSeasons: [],
        distribution: [],
      }; // 如果格式不正确，返回包含空数组的对象
    }
  } catch (error) {
    console.error('请求错误:', error);
    notification.error({
      message: '网络错误',
      description: '请求失败，请检查网络连接或稍后重试。',
      placement: 'topRight', // 提示栏位置
    });
    return {
      trends: [],
      peakSeasons: [],
      distribution: [],
    }; // 如果发生错误，返回包含空数组的对象
  }

}


//排行榜数据

//季节趋势
export const getRankingData = async () => {
  try {
    const response = await fetch('http://127.0.0.1:4523/m2/5909167-5596115-default/264811499', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('请求失败');
    }

    const result = await response.json(); // 获取完整的响应对象
    console.log('返回的数据:', result);

    // 检查返回的数据是否成功，并提取 data 数组
    if (result.success ) {
      return result.data; // 返回获取的航班数据
    } else {
      console.error('返回的数据格式不正确:', result);
      return {
        AirportRankingData: [],
        CityRankingData: [],
        AirlineRankingData: [],
      }; // 如果格式不正确，返回包含空数组的对象
    }
  } catch (error) {
    console.error('请求错误:', error);
    notification.error({
      message: '网络错误',
      description: '请求失败，请检查网络连接或稍后重试。',
      placement: 'topRight', // 提示栏位置
    });
    return {
      AirportRankingData: [],
      CityRankingData: [],
      AirlineRankingData: [],
    }; // 如果发生错误，返回包含空数组的对象
  }

}

export const getFlightData = async () => {
  // 这里是一个示例，您需要根据实际情况获取数据
  return [
    { lat: 34.0522, lng: -118.2437, flights: 500 }, // 洛杉矶
    { lat: 40.7128, lng: -74.0060, flights: 600 }, // 纽约
    { lat: 41.8781, lng: -87.6298, flights: 400 }, // 芝加哥
    // 添加更多城市数据
  ];
};