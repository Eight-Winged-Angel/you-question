<template>
  <div style="width: 100">
    <el-card style="padding: 0">
      <el-container style="height: 550px">
        <el-header style="height: 10%">
          <el-row>
            <el-col class="title" :span="3">
              <h1>第{{ index + 1 }}题</h1>
            </el-col>
            <el-col :span="1">
              <el-tag size="mini">
                {{ theExercise.score }}
              </el-tag>
            </el-col>
            <el-col :span="2">
              <QuestionTypeTag :name="theExercise.type" />
            </el-col>
          </el-row>
        </el-header>
        <el-divider></el-divider>
        <el-main style="height: 70%">
          <el-row>
            <el-col :span="16">
              <el-row style="margin-bottom: 60px">
                <el-col class="title" style="margin-top: 10px; margin-bottom: 20px; width: 90%">
                  <question-blank class="text-bold" :content="theExercise.content"></question-blank>
                </el-col>
              </el-row>
              <el-row>
                <el-form style="
                    display: flex;
                    flex-direction: column;
                    align-items: flex-start;
                  ">
                  <div v-if="theExercise.type === '单选题' || theExercise.type === '多选题'" style="width: 100%">
                    <custom-radio-group
                      :contents="theExercise.options"
                      :type="theExercise.type"
                      :checked.sync="theExercise.answer"
                    ></custom-radio-group>
                  </div>
                  <div v-if="theExercise.type === '判断题'" style="width: 100%">
                    <JudgeOption :checked.sync="theExercise.answer" />
                  </div>

                  <div v-if="theExercise.type === '填空题'" style="width: 100%">
                    <GapFill :checked.sync="theExercise.answer" />
                  </div>
                  <div v-if="theExercise.type === '应用题'" style="width: 100%">
                    <Essay :checked.sync="theExercise.answer" />
                  </div>
                </el-form>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-image :src="src"></el-image>
            </el-col>
          </el-row>
        </el-main>

        <el-divider></el-divider>
        <el-footer style="height: 20%">
          <el-row>
            <el-col :span="8" :offset="16">
              <el-button-group>
                <el-button type="primary" @click="save">保存</el-button>
                <el-button type="danger" @click="submit">提前交卷</el-button>
                <el-button type="success" @click="nextTopic">下一题</el-button>
              </el-button-group>
            </el-col>
          </el-row>
        </el-footer>
      </el-container>
    </el-card>
  </div>
</template>

<script>
import CustomRadioGroup from "@/components/option/CustomRadioGroup";
import JudgeOption from "@/components/option/JudgeOption";
import GapFill from "@/components/option/GapFill";
import QuestionTypeTag from "@/components/bank/QuestionTypeTag";
import Essay from "@/components/option/Essay";
import QuestionBlank from '@/components/bank/QuestionBlank'

export default {
  components: {
    QuestionBlank,
    CustomRadioGroup,
    JudgeOption,
    GapFill,
    QuestionTypeTag,
    Essay,
  },
  data() {
    return {
      src: require("../../assets/senlin.jpg"),
      theExercise: this.exercise,
    };
  },
  props: ["exercise", "index", "length"],
  mounted() {
    this.theExercise = this.exercise;
    console.log(
      this.index,
      "@@@@@",
      this.exercise,
      "theprac",
      this.exercise
    );
  },
  watch: {
    theExercise: {
      handler() {
        console.log("答题改变",this.theExercise);
        this.$emit("update:exercise", this.theExercise);
      },
      deep: true,
    },
    exercise:{
      handler(){
        this.theExercise = this.exercise;
      },
      deep:true
    }
  },
  methods: {
    // check(value) {
    //   console.log("选择", this.answer);
    // },
    nextTopic() {
      console.log("index", this.index);
      if (this.index == this.length - 1) {
        let data = {
          fromIndex: this.index,
          toIndex: this.index,
        };
        this.$bus.$emit("nextTopic", data);
        this.submit();
        return;
      } else {
        let data = {
          fromIndex: this.index,
          toIndex: this.index + 1,
        };
        this.$bus.$emit("nextTopic", data);
      }
    },
    save() {
      let data = {
        fromIndex: this.index,
        toIndex: this.index,
      };
      this.$bus.$emit("nextTopic", data);
    },
    submit() {
      let data = {
        fromIndex: this.index,
        toIndex: this.index,
      };
      this.$bus.$emit("switchTopic", data);
      // console.log("发起提交")
      this.$bus.$emit("submit");
    },
  },
};
</script>

<style scoped>
.text-bold {
  font-weight: bold;
}
.title {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-left: 12px;
}
.primary {
  border-radius: 10px;
}
.finish {
  border-radius: 10px;
  border-left: 4px solid rgb(64, 158, 255);
}
</style>