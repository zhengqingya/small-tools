<template>
  <my-base-wraper class="content">
    <hr />
    <span>打赏</span>
    <div>
      <img src="./payee_code_WeChat.jpg" />
    </div>
  </my-base-wraper>
</template>

<script>
export default {
  name: "Dashboard"
};
</script>

<style lang="scss" scoped>
.content {
  text-align: center;
  > span {
    font-size: 30px;
    margin-top: 50px;
  }
  > div > img {
    width: 300px;
    height: 400px;
    margin-top: 80px;
  }
}
</style>
