<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">讲师介绍</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 讲师基本信息 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img :src="this.teacherData.avatar" />
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{this.teacherData.name}}&nbsp;{{this.teacherData.level===1?'高级讲师':'首席讲师'}}</span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg"> {{this.teacherData.intro}}</span>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">
               {{this.teacherData.career}}
              </p>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主讲课程</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="this.courseData.length===0">
            <span class="c-666 fsize14 ml10 vam"
              >该教师暂未教授任何课程，小编正在催促中。。。</span
            >
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="this.courseData.length>0">
            <ul class="of">
              <li v-for="c in courseData" :key="c.id">
                <div class="cc-l-wrap">
                  <section class="course-img" style="height:200px">
                    <img
                    style="height:200px"
                      :src="c.cover"
                      class="img-responsive"
                    />
                    <div class="cc-mask">
                      <a
                        :href="'/course/'+c.id"
                        title="开始学习"
                        class="comm-btn c-btn-1"
                        >开始学习</a
                      >
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a
                      :title="c.title"
                      class="course-title fsize18 c-333"
                      >{{c.title}}</a
                    >
                  </h3>
                </div>
              </li>
          
            </ul>
            <div class="clear"></div>
          </article>
        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>
<script>
import teacher from "@/api/teacher/teacher";
export default {
  data() {
    return {
      teacherData:{},
      courseData:[]
    };
  },
  created() {
    if(this.$route.params&&this.$route.params.id){
      
      this.teacherId=this.$route.params.id
      this.getTeacherData()
    }
  },
  methods:{
      getTeacherData(){
        console.log("666");
        teacher.getTeacherData(this.teacherId).then(response=>{
          this.teacherData=response.data.data.teacher
          this.courseData=response.data.data.list
          console.log(this.teacherData);
          console.log(this.courseData);
        })
      }
  }
};
</script>