<template>
  <div style="width: 100%; height: 100%">
    <el-dialog title="添加练习" :visible.sync="dialogVisible">
      <PracticeDialog />
    </el-dialog>
    <el-container
      style="
        margin: auto;
        display: flex;
        justify-content: center;
        align-items: center;
      "
    >
      <el-header>
        <el-button type="success" @click="addPrac">添加练习</el-button>
      </el-header>
      <el-main>
        <ul style="margin-bottom: 5%; width: 90%">
          <li
            v-for="(item, index) of practices"
            :key="index"
            :name="index"
            style="margin-top: 1.5%"
          >
            <el-collapse v-model="activeNames" @change="handleChange">
              <el-collapse-item>
                <template slot="title">
                  <el-row
                    style="
                      width: 100%;
                      margin: auto;
                      display: flex;
                      align-items: center;
                      background-color: ;
                    "
                  >
                    <el-col
                      :span="16"
                      class="topic title"
                      style="font-size: 15px"
                    >
                      &nbsp;&nbsp;
                      {{ item.title }}
                    </el-col>
                    <el-col :span="2"></el-col>
                    <el-col
                      :span="6"
                      class="noticeDate"
                      style="background-color: "
                    >
                      <!-- 三种状态显示 -->

                      <!-- <el-tag
                      effect="plain"
                      type="success"
                      style="font-size:medium"
                      >已结束</el-tag> -->
                      <!-- <el-tag
                      effect="plain"
                      type="info"
                      style="font-size:medium"
                      >未开始</el-tag> -->
                      <el-tag effect="plain" type="" style="font-size: medium"
                        >{{item.state}}</el-tag
                      >

                      &nbsp;&nbsp;</el-col
                    >
                  </el-row>
                </template>

                <!-- 练习的简介 -->

                <div class="subTopic" font-size="14px">
                  {{ item.content }}
                </div>

                <el-divider>
                  <i class="el-icon-time"></i>
                </el-divider>

                <div class="subTopic" style="margin-top: 2%">
                  {{ item.startTime !== null ? item.startTime.substring(0,10):''}}&nbsp;——

                  {{ item.endTime !== null ? item.endTime.substring(0,10):'' }}
                  <!-- <el-divider direction="vertical"></el-divider> -->
                  <br />
                  {{ item.timeLimit }}
                </div>

                <el-divider>
                  <i class="el-icon-check"></i>
                </el-divider>
                <div class="stuTags">
                  <el-tag
                    class="stuName"
                    type="success"
                    v-for="(stu, stuNum) of item.finishStudents"
                    :key="stuNum"
                    >{{ stu }}</el-tag
                  >
                  <el-badge
                    value="!"
                    class="item stuName"
                    v-for="(stu2, stuNum2) of item.unfinishStudents"
                    :key="stuNum2"
                  >
                    <el-tag type="danger">{{ stu2 }}</el-tag>
                  </el-badge>
                </div>
              </el-collapse-item>
            </el-collapse>
          </li>
        </ul>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import PracticeDialog from "@/components/teacher/PracticeDialog";
export default {
  components: { PracticeDialog },
  mounted() {
    let that = this;
    this.$bus.$on('addFinish',()=>{
      that.dialogVisible = false;
    })

    this.practices.push(this.a, this.a, this.a);
  },
  watch: {
    $route: {
      handler() {
        var courseId = parseInt(this.$route.params.id);
        this.$store.commit('GET_COURSEID',courseId)
      courseId = parseInt(this.$store.state.courseId)
      // console.log("pppppppppppaaaa",)
        
        // console.log("现在在做通知",window.sessionStorage.getItem('token'),courseId,'course')
        axios
          .post("http://localhost:14514/teacher/course/practice/list", {
            
          },{
            headers: {
              token: this.$store.state.token
            },
            params:{
              'courseId': courseId, 
            page: 1 
            }
          
          })
          .then((response) => {
            this.practices = response.data.data.records;
            console.log("practiceeeeeeeeeeeeeeeeeeeee",response)
            // console.log("prac",this.practices)
          });
      },
      deep: true,
      immediate: true,
    },
  },
  data() {
    return {
      dialogVisible:false,
      activeNames: ["1"],
      practices: [],
      a: {
        practiceId: "1",
        title: "第一次单元测试",
        startTime: "2022/9/3 14:00:00",
        endTime: "2022/9/4 15:29:59",
        timeLimit: "90分钟",
        content: "考试的简介",
        state:'进行中',
        // {
        //   courseId:'',
        //   title:'',
        //   practiceId:'',
        //   practiceState:'',
        //   detail:
        //   {
        //     startTime:'',
        //     endTime:'',
        //     timeLimit:'',
        //     practiceIntroduction:''

        //   }
        // },

        finishStudents: [
          "张三",
          "王五",
          "赵六",
          "张三",
          "王五",
          "赵六",
          "张三",
          "王五",
          "赵六",
          "张三",
          "王五",
          "赵六",
          "张三",
          "王五",
          "赵六",
          "张三",
          "王五",
          "赵六",
        ],
        unfinishStudents: ["张下三", "王下五", "赵下六"],
      },
    };
  },
  methods: {
    addFinish() {
      this.dialogVisible = false;
      location.reload();
    },
    addPrac(){
      this.dialogVisible = true;

    },
    //点击课程，查看练习详情
    handleChange(val) {


      console.log(val);
    },
  },
};
</script>

<style scoped>
.el-header {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: #409eff;
  border-radius: 0.5em;
}
.el-main {
  margin: 20px;
  width: 100%;
  display: flex;
  justify-content: center;
  background-color: white;
}
.topic {
  display: flex;
  justify-content: start;
  align-items: center;
  font-size: large;
  font-family: "Microsoft YaHei";
  font-weight: bolder;
  border-left: solid 3px #409eff;
}

.topic:hover {
  color: #ff4040;
  /* background-color: #409eff; */
}

.subTopic {
  /* display: flex;
  flex-direction: column;
  align-items: flex-start;
  border-left: solid 3px #000;
  margin-top: 10px;
  padding-left: 10px; */
  font-size: 14px;
}

.stuTags {
  display: flex;
  justify-items: flex-start;
  margin-top: 10px;
  flex-wrap: wrap;
}
.item {
  margin-left: 40px;
}
.stuName {
  margin-left: 15px;
  margin-bottom: 15px;
}
.title {
  font-weight: bold;
  letter-spacing: 0.2em;
}
</style>
