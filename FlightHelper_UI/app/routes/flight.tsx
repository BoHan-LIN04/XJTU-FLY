import { Layout, Menu, Button } from 'antd';
import { Link, Outlet, useLocation, useNavigate } from '@remix-run/react';
import { logout } from '~/utils/auth';
import {
  CompassOutlined,
  GlobalOutlined,
  LineChartOutlined,
  CalendarOutlined,
  LogoutOutlined,
  AreaChartOutlined,
} from '@ant-design/icons';

const { Header, Sider, Content } = Layout;

export default function FlightLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  
  const selectedKey = location.pathname.split('/').pop() || 'smart-routing';

  const flightMenuItems = [
    {
      key: 'where-to-fly',
      icon: <GlobalOutlined />,
      label: <Link to="/flight/flights-search">航班搜索</Link>,
    },
    {
      key: 'smart-routing',
      icon: <CompassOutlined />,
      label: <Link to="/flight/smart-routing">智能拼接</Link>,
    },
    {
      key: 'price-analysis',
      icon: <LineChartOutlined />,
      label: <Link to="/flight/price-analysis">何时飞</Link>,
    },
    {
      key: 'flight-trends',
      icon: <AreaChartOutlined />,
      label: <Link to="/flight/flight-trends">航班趋势</Link>,
    },
    {
      key: 'season-trends',
      icon: <CalendarOutlined />,
      label: <Link to="/flight/season-trends">季节趋势</Link>,
    },
    {
      key: 'ranking',
      icon: <CalendarOutlined />,
      label: <Link to="/flight/ranking">排行榜</Link>,
    },
    {
      key: 'flight-heatmap',
      icon: <CalendarOutlined />,
      label: <Link to="/flight/flight-heatmap">航班热力图</Link>,
    }
  ];

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider>
        <div style={{ padding: '16px', textAlign: 'center' }}>
          <img src="/plane_logo.png" alt="Logo" style={{ width: '80%', marginBottom: '16px' }} />
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={[selectedKey]}
          items={flightMenuItems}
        />
      </Sider>
      <Layout>
        <Header style={{ 
          padding: '0 24px', 
          background: '#fff', 
          display: 'flex', 
          justifyContent: 'space-between',
          alignItems: 'center' 
        }}>
          <div style={{ fontWeight: 'bold', fontSize: '20px' }}>Welcome to Plane Helper！</div>
          <Button 
            type="primary" 
            icon={<LogoutOutlined />} 
            onClick={handleLogout}
          >
            Log out
          </Button>
        </Header>
        <Content style={{ padding: '24px' }}>
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  );
} 