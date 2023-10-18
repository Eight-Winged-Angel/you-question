<template>
  <div>
    <el-row>
      <el-col :offset="1" :span="22">
        <custom-radio
          v-for="(value, index) of contents"
          :key="index"
          :option="value"
          :type="type"
          :checked.sync="theChecked"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CustomRadio from "@/components/option/CustomRadio";

export default {
  components: { CustomRadio },
  props: {
    contents: {
      type: Array,
      default: () => {
        let ids = ["A", "B", "C", "D"];
        return ids.map((currentValue) => {
          return { id: currentValue, content: currentValue + "选项" };
        });
      },
    },
    checked: {
      type: Array,
      default: () => {
        let checked = [];
        return checked;
      },
    },
    type: { type: String, default: "单选题" },
  },
  mounted(){
    theChecked=this.checked;
  },
  data() {
    return {
      theChecked:this.checked
    };
  },
  watch: {
    checked() {
      this.theChecked = this.checked;
    },
    theChecked(){
      this.$emit("update:checked", this.theChecked);
    }
  },
};
</script>

<style scoped>
</style>