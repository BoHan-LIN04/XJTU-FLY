import React, { useEffect } from 'react';

/// <reference types="google.maps" />
const FlightHeatmap: React.FC = () => {
  useEffect(() => {
    // 初始化地图
    const initMap = () => {
      const map = new google.maps.Map(document.getElementById("map") as HTMLElement, {
        zoom: 4, // 缩放级别
        center: { lat: 37.7749, lng: -95.7129 }, // 美国的中心点 (大致位置)
        mapTypeId: "satellite", // 设置为卫星视图
      });

      // 创建热力图层
      const heatmap = new google.maps.visualization.HeatmapLayer({
        data: getPoints(),
        map: map,
        radius: 20, // 设置热力图的半径
        opacity: 0.6, // 设置热力图的透明度
      });

      // 控制热力图显示的按钮
      document
        .getElementById("toggle-heatmap")!
        .addEventListener("click", toggleHeatmap);

      // 更新热力图的颜色梯度
      document
        .getElementById("change-gradient")!
        .addEventListener("click", changeGradient);

      // 更新热力图的透明度
      document
        .getElementById("change-opacity")!
        .addEventListener("click", changeOpacity);

      // 更新热力图的半径
      document
        .getElementById("change-radius")!
        .addEventListener("click", changeRadius);

      // 热力图切换
      function toggleHeatmap(): void {
        heatmap.setMap(heatmap.getMap() ? null : map);
      }

      // 更改热力图的渐变
      function changeGradient(): void {
        const gradient = [
          "rgba(0, 255, 255, 0)",
          "rgba(0, 255, 255, 1)",
          "rgba(0, 191, 255, 1)",
          "rgba(0, 127, 255, 1)",
          "rgba(0, 63, 255, 1)",
          "rgba(0, 0, 255, 1)",
          "rgba(0, 0, 223, 1)",
          "rgba(0, 0, 191, 1)",
          "rgba(0, 0, 159, 1)",
          "rgba(0, 0, 127, 1)",
          "rgba(63, 0, 91, 1)",
          "rgba(127, 0, 63, 1)",
          "rgba(191, 0, 31, 1)",
          "rgba(255, 0, 0, 1)",
        ];

        heatmap.set("gradient", heatmap.get("gradient") ? null : gradient);
      }

      // 更改热力图的半径
      function changeRadius(): void {
        heatmap.set("radius", heatmap.get("radius") ? null : 30);
      }

      // 更改热力图的透明度
      function changeOpacity(): void {
        heatmap.set("opacity", heatmap.get("opacity") ? null : 0.4);
      }
    };

    // 加载 Google Maps 和 Heatmap 库
    const loadGoogleMaps = () => {
      const script = document.createElement('script');
      script.src = `https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=visualization`;
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);
      script.onload = initMap; // 确保在加载后调用 initMap
    };

    loadGoogleMaps();
  }, []);

  // 示例热力图数据：美国的航班数据
  const getPoints = () => {
    return [
      { lat: 40.7128, lng: -74.0060, count: 10 }, // New York
      { lat: 34.0522, lng: -118.2437, count: 8 }, // Los Angeles
      { lat: 41.8781, lng: -87.6298, count: 6 }, // Chicago
      { lat: 29.7604, lng: -95.3698, count: 7 }, // Houston
      { lat: 39.7392, lng: -104.9903, count: 4 }, // Denver
      { lat: 33.4484, lng: -112.0740, count: 5 }, // Phoenix
      { lat: 51.5074, lng: -0.1278, count: 9 }, // London (示例国际航班)
      { lat: 48.8566, lng: 2.3522, count: 7 }, // Paris (示例国际航班)
      // 你可以继续添加更多的点...
    ];
  };

  return (
    <div>
      <div id="map" style={{ width: "100%", height: "500px" }}></div>

      {/* 控制按钮 */}
      <button id="toggle-heatmap">Toggle Heatmap</button>
      <button id="change-gradient">Change Gradient</button>
      <button id="change-opacity">Change Opacity</button>
      <button id="change-radius">Change Radius</button>
    </div>
  );
};

export default FlightHeatmap;