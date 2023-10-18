<template >
  <el-breadcrumb separator-class="el-icon-arrow-right"  style="color:red">
    <el-breadcrumb-item class="item" v-for="(item, index) in items" 
    :key="index">{{ item }}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
import { watch } from 'vue'
export default {
  props: {
    items: {type: Array}
  },
  data(){
    return{
    }
  },
  mounted(){
    
  },
  methods:{
    changeId(value){
      console.log("面包屑改变",value);
      this.items.splice(1,1,value[1]);
    }
  }

}
</script>

<style scoped>
.item {
  font-size: large;
  padding: 1em 0;
  letter-spacing: 0.1em;
 
}
</style>