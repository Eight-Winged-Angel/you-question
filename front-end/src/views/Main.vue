<template>
  <div>
    <!-- <img src="../assets/senlin.jpg" /> -->
    
    <!-- <el-carousel height="600px" >
      <el-carousel-item v-for="(image, index) in images" :key="index">
        <img :src="image" height="600px"/>
      </el-carousel-item>
    </el-carousel> -->
    <el-row >
      <el-col :span="18" :offset="3">
        <MyCourse/>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import MyCourse from '../components/mycourse/MyCourse';
export default {
  components:{MyCourse,},
  name: "Main",
  props: [],
  data() {
    return {
      images: [
        require('../assets/senlin.jpg'),
        require('../assets/haiyang.jpg'),
        ],
    };
  },
  methods: {},
};
</script>

<style>
</style>