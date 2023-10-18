<template>
  <div>
    <div
      style="
        width: 100%;
        background: #f8f9fa;
        border-radius: 10px;
        padding: 25px;
      "
      v-for="(Semester, index) of Semesters"
      :key="index"
    >
      <top-funcs :Semester="Semester" />
      <CourseCard
        v-for="(course, index2) of Semester.Courses"
        :key="index2"
        :Course="course"
      />
    </div>
  </div>
</template>

<script>
import TopFuncs from "./top_funcs/TopFuncs";
import CourseCard from "./course_card/CourseCard";

export default {
  name: "SemesterCourse",
  components: { TopFuncs, CourseCard },
  data() {
    return {
      Semesters: [
        {
          SemesterNum: [2021, 2],
          Courses: [
            {
              CourseName: "运筹学",
              CourseId: "3FBKSR",
              Students: [
                {
                  StudentName: "张三",
                  StudentId: "123456789",
                },
                {
                  StudentName: "李四",
                  StudentId: "987654321",
                },
              ],
            },
          ],
        },
      ],
    };
  },
};
</script>

<style>
p {
  border-radius: ;
}
</style>