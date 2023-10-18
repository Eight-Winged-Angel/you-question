<template>
  <div>
    <el-form :model="form" label-width="5em" label-position="right">
      <el-form-item style="width: 100%" >
        <span slot="label" class="text-bold" >邀请码</span>
        <el-input
          type="textarea"
          autosize
          placeholder="请输入练习邀请码"
          size="small"
          v-model="this.form.inviteCode"
          maxlength="255"
          show-word-limit
          @input="invite"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <span slot="label" class="text-bold">标题</span>
        <el-input type="textarea" autosize placeholder="请输入标题" size="small"
                  v-model="form.title" maxlength="255" show-word-limit></el-input>
      </el-form-item>
      <el-form-item>
        <span slot="label" class="text-bold">说明</span>
        <el-input type="textarea" autosize placeholder="请输入说明" size="small"
                  v-model="form.content" maxlength="255" show-word-limit></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="15">
          <el-form-item>
            <span slot="label" class="text-bold">起止</span>
            <el-date-picker v-model="form.timeRange" type="datetimerange" range-separator="-" size="small"
                            start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="8">
          <el-form-item>
            <span slot="label" class="text-bold">限时</span>
            <el-tooltip class="item" content="格式：数字+小写英文字母单位（d-天、h-时、min-分、s-秒）" placement="top-end">
              <el-input placeholder="无限制" size="small" v-model="form.timeLimit" :clearable="true"></el-input>
            </el-tooltip>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item v-if="JSON.stringify(finalPrac) != '{}'">
        <el-descriptions :column="3" size="mini" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-edit"></i>
              题量
            </template>
            {{ finalPrac.count }}道
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-edit-outline"></i>
              满分
            </template>
            {{ finalPrac.score }}分
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-share"></i>
              邀请码
            </template>
            {{ finalPrac.inviteCode }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              开始时间
            </template>
            {{ finalPrac.startTime || "无限制" }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              限时
            </template>
            {{ finalPrac.timeLimit || "无限制" }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-notebook-1"></i>
              所属课程
            </template>
            {{ finalPrac.course || "暂无" }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              结束时间
            </template>
            {{ finalPrac.endTime || "无限制" }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-bell"></i>
              说明
            </template>
            {{ finalPrac.content || "暂无" }}
          </el-descriptions-item>
        </el-descriptions>
      </el-form-item>
      <el-row class="btnBottom">
        <el-col :span="4">
          <el-button icon="el-icon-arrow-left" @click="showPrac"
            >使用已有练习</el-button
          >
        </el-col>
        <el-col :span="4" :offset="16">
          <el-button type="primary" @click="addFinish">确认添加</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-drawer
      title="已有练习"
      :visible.sync="showDrawer"
      :direction="direction"
      :append-to-body="true"
    >
      <ul>
        <li
          v-for="(item, index) in practiceTableData"
          :key="index"
          style="width: 100%"
        >
          <el-popover placement="right" trigger="hover">
            <el-descriptions :column="3" size="mini" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-edit"></i>
                  题量
                </template>
                {{ item.count }}道
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-edit-outline"></i>
                  满分
                </template>
                {{ item.score }}分
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-share"></i>
                  邀请码
                </template>
                {{ item.inviteCode }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-date"></i>
                  开始时间
                </template>
                {{ item.startTime || "无限制" }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-time"></i>
                  限时
                </template>
                {{ item.timeLimit || "无限制" }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-notebook-1"></i>
                  所属课程
                </template>
                {{ item.course || "暂无" }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-date"></i>
                  结束时间
                </template>
                {{ item.endTime || "无限制" }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-bell"></i>
                  说明
                </template>
                {{ item.content || "暂无" }}
              </el-descriptions-item>
            </el-descriptions>
            <el-card
              @click.native="choosePrac(index)"
              class="box-card"
              slot="reference"
            >
              <el-row>
                <span>ID : {{ item.id }}</span>
              </el-row>
              <el-row>
                <span>内容 : {{ item.title }}</span>
              </el-row>
            </el-card>
          </el-popover>
        </li>
      </ul>
    </el-drawer>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  mounted() {
    this.practiceTableData = this.$store.state.practiceTableData;
  },
  data() {
    return {
      form: {
        inviteCode: '1aT5mcyXv_7q',
        title: '示例标题',
        content: '示例说明',
        timeRange: '',
        timeLimit: '2h'
      },
      showDrawer: false,
      direction: "ltr",
      practiceTableData: [],
      finalPrac: {},
    };
  },
  methods: {
    handleClose() {},
    showPrac() {
      this.showDrawer = true;
    },
    addFinish() {
      // console.log(this.form,"yyyyyyyyyuuuuuuuuusssssss")
      axios.post('http://localhost:14514/teacher/course/practice/add', {
        inviteCode: this.form.inviteCode,
        courseId: this.$store.state.currentCourseID,
        title: this.form.title,
        startTime: this.form.timeRange[0],
        endTime: this.form.timeRange[1],
        timeLimit: this.form.timeLimit,
        content: this.form.content
      },{
        headers: {
          'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
        }
      }).then(res => {
        if (res.data.code === '00000') {
          // this.finalPrac = res.data.data
          // console.log("ssssssssssssssssssssaaaaaaaa")
        }
        console.log("ssssssssssssssssssssaaaaaaaa",res)
      })
      // 蒋选中练习的ID发送给服务器添加在课程练习列表中
      // console.log("ssssssssssssssssssssaaaaaaaa")
      this.$bus.$emit('addFinish');
    },
    choosePrac(index) {
      this.finalPrac = this.practiceTableData[index];
      this.form.inviteCode = this.practiceTableData[index].inviteCode;
      console.log("选中练习", index, this.form);
      this.showDrawer = false;
    },
    invite() {
      axios.get('http://localhost:14514/teacher/course/practice/invite', {
        headers: {
          'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
        },
        params: {
          'inviteCode': this.form.inviteCode
        }
      }).then(res => {
        if (res.data.code === '00000') {
          this.finalPrac = res.data.data
        }
      })
    }
  },
};
</script>

<style scoped>
.el-form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.el-card {
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-items: center;
}
.el-card:hover {
  height: 120px;
  background-color: rgb(240, 242, 245);
}
.btnBottom {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
</style>