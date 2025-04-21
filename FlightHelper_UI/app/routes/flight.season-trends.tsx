import { Card, Row, Col, Form, Tag, Radio, AutoComplete, Button } from 'antd';
import {  Column, Pie, Line } from '@ant-design/plots';
import { RocketOutlined, SearchOutlined } from '@ant-design/icons';
import { useState } from 'react';
import { SeasonTrendsCriteria,SeasonTrendsData } from '~/utils/types';
import {airports} from 'public/assets/cities'
import { submitSeasonTrendsFilters } from '~/utils/api';

export default function SeasonTrends() {
  const [activeTab, setActiveTab] = useState('overview'); // 'overview' 或 'price'
  const [filters, setFilters] = useState<SeasonTrendsCriteria>({
    departureCity: '',
    arrivalCity: '',
  });
  const [seasonData, setSeasonData] = useState<SeasonTrendsData>({
    trends: [],
    peakSeasons: [],
    distribution: [],
  });
  // 更新出发地
  const handleDepartureChange = (value: string) => {
    setFilters((prev) => ({ ...prev, departureCity: value }));
  };

  // 更新到达地
  const handleArrivalChange = (value: string) => {
    setFilters((prev) => ({ ...prev, arrivalCity: value }));
  };


   // 提交筛选条件到后端
   const handleSubmit = async () => {
    const data = await submitSeasonTrendsFilters(filters);

    // 检查返回的数据是否成功，并提取 data 数组
    if (data.trends.length > 0) {
      setSeasonData(data); // 更新航班数据状态
    } else {
      setSeasonData(
        {
          trends: [],
          peakSeasons: [],
          distribution: [],
        }
      ); // 如果格式不正确，设置为空数组
    }
};
   // 生成自动补全选项
   const options = airports.map(loc => ({
    value: `${loc.value}`, // 显示地点和三字码
  }));

  return (
    <div>
      <Card style={{ marginBottom: 24 }}>
        <Form layout="horizontal">
          <Row gutter={16}>
            <Col span={7}>
              <Form.Item label="departure city">
                <AutoComplete
                  options={options}
                  onChange={handleDepartureChange}
                  placeholder="Please enter the departure city"
                  prefix={<RocketOutlined rotate={-45} />}
                />
              </Form.Item>
            </Col>
            <Col span={7}>
              <Form.Item label="arrival city">
                <AutoComplete
                  options={options}
                  onChange={handleArrivalChange}
                  placeholder="Please enter the arrive city"
                  prefix={<RocketOutlined rotate={45} />}
                />
              </Form.Item>
            </Col>
            <Col span={4}>
              <Button type="primary" icon={<SearchOutlined />} block onClick={handleSubmit}>
                Search Airlines
              </Button>
            </Col>
          </Row>
        </Form>
          <Col>
            <Radio.Group value={activeTab} onChange={e => setActiveTab(e.target.value)}>
              <Radio.Button value="overview">季节概览</Radio.Button>
              <Radio.Button value="price">价格波动</Radio.Button>
            </Radio.Group>
          </Col>
      </Card>

      {activeTab === 'price' ? (
        <Card title="季节性价格波动">
          <Column
            data={seasonData.trends}
            xField="month"
            yField="avgPrice"
            label={{
              position: 'top',
            }}
            meta={{
              avgPrice: {
                alias: '平均票价',
              }
            }}
          />
        </Card>
      ) : (
        <>
          <Row gutter={24}>
            <Col span={24}>
              <Card title="全年客流量和航班数趋势" style={{ marginBottom: 24 }}>
                <Line
                  data={seasonData.trends}
                  xField="month"
                  yField="passengers"
                  smooth
                  point={{
                    size: 5,
                    shape: 'diamond',
                  }}
                  meta={{
                    passengers: {
                      alias: '旅客数量',
                    }
                  }}
                />
              </Card>
            </Col>
          </Row>

          <Card title="季节分析">
            <Row gutter={24}>
              <Col span={16}>
                <div style={{ marginBottom: 24 }}>
                  <h3 style={{ marginBottom: 16 }}>旺季分布</h3>
                  <Row gutter={[16, 16]}>
                    {seasonData.peakSeasons.map((season, index) => (
                      <Col span={6} key={index}>
                        <Card bodyStyle={{ padding: 16, textAlign: 'center' }}>
                          <h4 style={{ marginBottom: 8 }}>{season.season}</h4>
                          <p style={{ marginBottom: 8, color: '#666' }}>{season.period}</p>
                          <Tag color="red">{season.priceIncrease}</Tag>
                        </Card>
                      </Col>
                    ))}
                  </Row>
                </div>
              </Col>
              <Col span={8}>
                <div>
                  <h3 style={{ marginBottom: 16 }}>季节分布</h3>
                  <Pie
                    data={seasonData.distribution}
                    angleField="value"
                    colorField="type"
                    radius={0.8}
                    label={{
                      type: 'outer',
                    }}
                    legend={{
                      position: 'bottom'
                    }}
                  />
                </div>
              </Col>
            </Row>
          </Card>
        </>
      )}
    </div>
  );
} 