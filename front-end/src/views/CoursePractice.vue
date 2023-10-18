<template>
  <div class="box">
    <div class="box">
      <ul
        class="list"
        v-infinite-scroll="infiniteLoad"
        infinite-scroll-disabled="disabled"
      >
        <li v-for="(test, index) in practices" :key="index" class="list-item">
          <el-card @click.native="oneTest(test)" class="box-card">
            <el-row>
              <el-row style="margin-bottom: 1.6%">
                <span class="title" style="text-align: center">{{
                  test.title
                }}</span>
              </el-row>
              <el-row>
                <span class="title" style="text-align: center">
                  {{ dataTime(test.startTime) }}——{{
                    dataTime(test.endTime)
                  }}</span
                >
              </el-row>
            </el-row>
          </el-card>
          <!-- {{ index }} -->
        </li>
      </ul>
      <!--  -->
      <p v-if="loading" style="margin-top: 10px" class="loading">
        <span></span>
      </p>
      <p
        v-else-if="noMore"
        style="margin-top: 10px; font-size: 13px; color: #ccc"
      >
        没有更多了
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: ["courseID"],
  // deactivated(){
  //   console.log('practive组件失活');
  //   this.$router.back();
  // },
  activated() {
    console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    this.courseId = this.$store.state.currentCourseID;
    console.log("课程id：", this.courseId);
    let data = {
      page: this.page,
      courseId: this.courseId,
    };
    this.page += 1;
    axios
      .get("/student/course/practice/list", {
        headers: {
          token: this.$store.state.token,
        },
        params: {
          courseId: this.courseId,
          page: this.page++,
        },
      })
      .then((response) => {
        console.log("练习列表获取成功", response);
        this.practices.splice(0,this.practices.length,...(response.data.data.records));
        console.log(this.practices);
        // window.sessionStorage.setItem("courseId", response.data.data);
      })
      .catch((resp) => {
        console.log("练习表失败", response);
      });
  },
  watch: {
    $route: {
      handler() {
        this.courseId = parseInt(this.$store.state.currentCourseID);
        console.log("课程id：", this.courseId);
        let data = {
          page: this.page++,
          courseId: this.courseId,
        };
        axios
          .get("/course/practice/list", {
            headers: {
              token: this.$store.state.token,
            },
            params: data,
          })
          .then((response) => {
            console.log("练习表成功", response);
            this.practices.unshift(response.data.data.records);
            console.log(this.practices);
            // window.sessionStorage.setItem("courseId", response.data.data);
          })
          .catch((resp) => {
            console.log("练习表失败", response);
          });
      },
      deep: true,
    },
  },
  methods: {
    oneTest(test) {
      console.log("选择练习", test.id);
      axios
        .get("/student/course/practice/read",{
          headers: {
            token: this.$store.state.token,
          },
          params: {
            practiceId: test.id,
          },
        })
        .then((response) => {
          console.log("获取练习题成功", response);
          this.$store.commit("SET_PAPER", response.data.data);
          this.$store.commit("SET_PRACTICEID",test.id);

          this.$router.push({
            name: "oneexam",
          });
        })
        .catch((resp) => {
          console.log("练习表失败", response);
        });

    },
    infiniteLoad() {
      console.log("触底了yoyoyoyo");
      //滑到底部时进行加载

      // this.loading = true;
      // setTimeout(() => {
      //   let length = this.practices.push(this.a, this.a);
      //   console.log("无限加载", length, "loading", this.loading);
      //   this.showLoading();
      // }, 1500);
    },
    showLoading() {
      this.loading = false;
    },
    dataTime(string) {
      if (string != null) {
        return string.substr(0, 10);
      } else {
        return;
      }
    },
  },
  data() {
    return {
      loading: false,
      page: 1,
      totalPages: 20, //取后端返回内容的总页数
      courseId: this.$route.params.id,
      a: {
        name: "第一次单元测试",
        time: "2022/9/3",
        paperId: 1,
      },

      practices: [],
    };
  },
  computed: {
    noMore() {
      //当起始页数大于总页数时停止加载
      return this.practices.length >= this.totalPages - 1;
    },
    disabled() {
      return this.loading || this.noMore;
    },
  },
};
</script>

<style scoped>
.el-card {
  height: 100px;
}
.el-card:hover {
  height: 120px;
  background-color: rgb(240, 242, 245);
}

#box {
  width: 100%;
  position: absolute;
  overflow-y: auto;
}
.box {
  width: 100%;
  margin: 0 auto;
  overflow: auto;
}
.list {
  padding: 0;
  height: 500px;
  overflow: auto;
}
.list-item {
  width: 100%;
}
.loading span {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 2px solid #409eff;
  border-left: transparent;
  animation: rotate 0.5s linear infinite;
  border-radius: 50%;
}
.title {
  font-weight: bold;
  letter-spacing: 0.2em;
}
@keyframes rotate {
  0% {
    transform: rotate(0);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>