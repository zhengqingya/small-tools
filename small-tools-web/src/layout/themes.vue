<template></template>
<script setup lang="ts">

import themes from '@/utils/themes';
import { colorMix } from '@/utils/tool';

//  ************ 主题 **************
// themes 对象包含 defaultTheme、darkTheme 两个属性即默认主题与深色主题
let currentSkinName = 'darkTheme';
let themeObj: any;
switchTheme();
// 根据不同的主题类型 获取不同主题数据
function switchTheme() {
  themeObj = themes[currentSkinName];
  getsTheColorScale();
  // 设置css 变量
  Object.keys(themeObj).map((item) => {
    console.log(item + ':' + themeObj[item]);
    document.documentElement.style.setProperty(item, themeObj[item]);
  });
}
// 获取色阶
function getsTheColorScale() {
  const colorList = [
    'primary',
    'success',
    'warning',
    'danger',
    'error',
    'info',
  ];
  const prefix = '--el-color-';
  colorList.map((colorItem) => {
    for (let i = 1; i < 10; i += 1) {
      if (i === 2) {
        // todo 深色变量生成未完成 以基本色设置
        themeObj[`${prefix}${colorItem}-dark-${2}`] = colorMix(
          themeObj[`${prefix}black`],
          themeObj[`${prefix}${colorItem}`],
          1
        );
      } else {
        themeObj[`${prefix}${colorItem}-light-${10 - i}`] = colorMix(
          themeObj[`${prefix}white`],
          themeObj[`${prefix}${colorItem}`],
          i * 0.1
        );
      }
    }
  });
}
</script>
