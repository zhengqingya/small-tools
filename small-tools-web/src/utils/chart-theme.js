/**
 * 设置图表黑色主题下的label颜色和line颜色
 * @param {obj} chart
 * @param {string} xAxis
 * @param {string} yAxis
 */
export const setChartDark = (chart, xAxis, yAxis) => {
  chart.axis(yAxis, {
    label: {
      textStyle: {
        fill: "#7189C8"
      }
    },
    grid: {
      lineStyle: {
        stroke: "#1D2F5E"
      }
    }
  });
  chart.axis(xAxis, {
    label: {
      textStyle: {
        fill: "#7189C8"
      }
    },
    line: {
      stroke: "#1D2F5E"
    }
  });
};

/**
 * 设置图表默认主题下的label颜色和line颜色
 * @param {obj} chart
 * @param {string} xAxis
 * @param {string} yAxis
 */
export const setChartDefault = (chart, xAxis, yAxis) => {
  chart.axis(yAxis, {
    label: {
      textStyle: {
        fill: "#404040"
      }
    },
    grid: {
      lineStyle: {
        stroke: "#d9d9d9"
      }
    }
  });
  chart.axis(xAxis, {
    label: {
      textStyle: {
        fill: "#404040"
      }
    },
    line: {
      stroke: "#b6b6b6"
    }
  });
};
