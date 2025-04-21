import { Card, Row, Col, Typography,Alert } from 'antd';
import { motion } from 'framer-motion';

import { AirportRankingData, CityRankingData, AirlineRankingData } from '~/utils/types';
import { getRankingData } from '~/utils/api';
import { useState, useEffect } from 'react';
const { Title } = Typography;

const Rankings = () => {

    const [airportRankingData, setAirportRankingData] = useState<AirportRankingData[]>([]);
    const [cityRankingData, setCityRankingData] = useState<CityRankingData[]>([]);
    const [airlineRankingData, setAirlineRankingData] = useState<AirlineRankingData[]>([]);
    // 获取排行榜数据
    useEffect(() => {
        const fetchRankingData = async () => {
            const data = await getRankingData();
            console.log(data);
            setAirportRankingData(data.airportData);
            setCityRankingData(data.cityData);
            setAirlineRankingData(data.airlineData);
        };
        fetchRankingData();
    }, []);

  

  return (
    <div style={{ padding: '24px' }}>
      <Title level={2}>排行榜 ✨</Title>
      <Col span={24}>
        <Alert
             message="排行榜数据仅供参考"
            description="基于2024年1月1日至2024年12月31日的数据"
            type="info"
            showIcon
            style={{ marginBottom: 16 }}
                      />
        </Col>

      <Row gutter={32}>
        {/* 机场吞吐量榜单 */}
        <Col span={8}>
          <motion.div whileHover={{ scale: 1.2}} transition={{ duration: 0.3 }}>
            <Card title="机场吞吐量榜单" bordered={false}>
              <ul style={{ padding: 0 }}>
                {airportRankingData && airportRankingData.length > 0 ? (
                  airportRankingData.map((item, index) => (
                    <li key={item.name} style={{ display: 'flex', alignItems: 'center', marginBottom: 16 }}>
                      <span style={{ marginRight: 8 }}>{index === 0 ? '🏆' : index === 1 ? '🥈' : index === 2 ? '🥉' : ''}</span>
                      <span style={{ fontWeight: 'bold', fontSize: '16px' }}>{item.name}</span>
                      <span style={{ marginLeft: 'auto' }}>{item.throughput} ⬆️</span>
                    </li>
                  ))
                ) : (
                  <li>数据加载中...</li>
                )}
              </ul>
            </Card>
          </motion.div>
        </Col>

        {/* 热门城市榜 */}
        <Col span={8}>
          <motion.div whileHover={{ scale: 1.2 }} transition={{ duration: 0.3 }}>
            <Card title="热门城市榜" bordered={false}>
              <ul style={{ padding: 0 }}>
                {cityRankingData && cityRankingData.length > 0 ? (
                  cityRankingData.map((item, index) => (
                    <li key={item.name} style={{ display: 'flex', alignItems: 'center', marginBottom: 16 }}>
                      <span style={{ marginRight: 8 }}>{index === 0 ? '🏆' : index === 1 ? '🥈' : index === 2 ? '🥉' : ''}</span>
                      <span style={{ fontWeight: 'bold', fontSize: '16px' }}>{item.name}</span>
                      <span style={{ marginLeft: 'auto' }}>airlines: {item.flights}, airports: {item.airports} ⬆️</span>
                    </li>
                  ))
                ) : (
                  <li>数据加载中...</li>
                )}
              </ul>
            </Card>
          </motion.div>
        </Col>

        {/* 航空公司实力榜 */}
        <Col span={8}>
          <motion.div whileHover={{ scale: 1.2 }} transition={{ duration: 0.3 }}>
            <Card title="航空公司实力榜" bordered={false}>
              <ul style={{ padding: 0 }}>
                {airlineRankingData && airlineRankingData.length > 0 ? (
                  airlineRankingData.map((item, index) => (
                    <li key={item.name} style={{ display: 'flex', alignItems: 'center', marginBottom: 16 }}>
                      <span style={{ marginRight: 8 }}>{index === 0 ? '🏆' : index === 1 ? '🥈' : index === 2 ? '🥉' : ''}</span>
                      <span style={{ fontWeight: 'bold', fontSize: '16px' }}>{item.name}</span>
                      <span style={{ marginLeft: 'auto' }}>flights: {item.flights} ⬆️</span>
                    </li>
                  ))
                ) : (
                  <li>数据加载中...</li>
                )}
              </ul>
            </Card>
          </motion.div>
        </Col>
      </Row>
    </div>
  );
};

export default Rankings;