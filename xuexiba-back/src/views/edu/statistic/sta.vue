<template>
  <div class="app-container">
    <div class="chart-container">
      <div id="chart" class="chart" style="height: 500px; width: 100%" />
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";

import staApi from "@/api/statistic/statistic";

export default {
  data() {
    return {
      searchObj: {},
      btnDisabled: false,
      xData: [],
      yData: [],
    };
  },
  created() {
    this.getDefault();
  },
  methods: {
    getDefault() {
      staApi.getDefault().then((response) => {
        this.xData = response.data.x;
        this.yData = response.data.y;
        this.init()
      });
    },

    init() {
      this.chart = echarts.init(document.getElementById("chart"));
      var option = {
        title: {
          text: "近日数据一览表",
        },

        legend: {},
        tooltip: {
          trigger: "axis",
          showContent: true,
        },
        dataset: {
          source: [
            this.xData,
            this.yData.course,
            this.yData.video,
            this.yData.login,
            this.yData.register,
          ],
        },
        xAxis: { type: "category", data: this.xData },
        yAxis: { gridIndex: 0 },
        grid: { top: "55%" },
        series: [
          {
            type: "line",
            smooth: true,
            seriesLayoutBy: "row",
            data: this.yData.course,
          },
          {
            type: "line",
            smooth: true,
            seriesLayoutBy: "row",
            data: this.yData.video,
          },
          {
            type: "line",
            smooth: true,
            seriesLayoutBy: "row",
            data: this.yData.login,
          },
          {
            type: "line",
            smooth: true,
            seriesLayoutBy: "row",
            data: this.yData.register,
          },
          {
            type: "pie",
            id: "pie",
            radius: "30%",
            center: ["50%", "25%"],
            label: {
              formatter: "{b}: {@[1]} ({d}%)",
            },
            // encode: {
            //   itemName: 'product',
            //     value: '2012',
            //         tooltip: '2012'
            // },
          },
        ],
      };

      this.chart.on("updateAxisPointer", function (event) {
        var xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          var dimension = xAxisInfo.value + 1;
         this.chart.setOption({
            series: {
              id: "pie",
              label: {
                formatter: "{b}: {@[" + dimension + "]} ({d}%)",
              },
              encode: {
                value: dimension,
                tooltip: dimension,
              },
            },
          });
        }
      });

      this.chart.setOption(option);
    },
  },
};
</script>
