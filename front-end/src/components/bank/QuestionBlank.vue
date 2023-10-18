<template>
  <span>
    <span v-for="(value, index) in content.split(/{}/)" :key="index">
      <el-tag size="mini" v-if="index > 0" style="margin-left: 0.5em; margin-right: 0.5em">{{ index }}</el-tag>
      <span>{{ value }}</span>
    </span>
  </span>
</template>

<script>
export default {
  props: {
    content: { type: String }
  }
}
</script>

<style scoped>

</style>