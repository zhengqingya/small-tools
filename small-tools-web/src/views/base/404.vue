<template>
  <div class="wscn-http404-container">
    <div class="img">
      <img class="pic-404__parent" src="../../assets/404_images/404.png" alt="404" />
    </div>
    <div class="text">
      <h3>页面已失联！</h3>
      <p>抱歉，当你看到这个页面的时候，表示你找的页面已经失联，别担心，你可以返回首页继续操作，我们将尽快找回丢失的页面！</p>
      <router-link to="/">
        <el-button type="primary">返回首页</el-button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "Page404"
};
</script>

<style lang="scss" scoped>
.wscn-http404-container {
  transform: translate(-50%, -50%);
  position: absolute;
  top: 40%;
  left: 50%;
  .img,
  .text {
    display: inline-block;
    vertical-align: middle;
  }
  .text {
    width: 324px;
    margin-left: 46px;
    h3 {
      font-size: 18px;
      font-weight: bold;
      color: #2288fc;
    }
    p {
      color: #999;
      line-height: 26px;
      font-size: 14px;
      margin-bottom: 20px;
    }
  }
}
</style>
