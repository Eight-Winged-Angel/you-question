<template>
  <el-container>
    <img :src="banner" alt="banner" style="width: 320px"/>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      banner: require("../../assets/banner.png")
    }
  }
}
</script>

<style scoped>

</style>
