<template>
  <div class="app-container">
  

    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终提交" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 所属分类 TODO -->
      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>
      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>

      <!-- 课程封面 TODO -->
      <el-form-item label="课程封面(点击图片更换)">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/fileoss'"
        >
          <img :src="courseInfo.cover" style="width: 400px; height: 200px" />
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />
        元
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate()"
          >保存并下一步</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from "@/api/course/course";
import subject from "@/api/subject/subject";
import Tinymce from "@/components/Tinymce"; //引入组件
export default {
  //声明组件
  components: { Tinymce },
  data() {
    return {
      courseInfo: {
        title: "",
        subjectId: "",
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover:
          "https://onlineeducate.oss-cn-beijing.aliyuncs.com/2021/09/10/5.jpg",
        price: 0,
      },
      saveBtnDisabled: false, // 保存按钮是否禁用
      BASE_API: process.env.BASE_API, // 接口API地址
      teacherList: [], //封装所有的讲师
      subjectOneList: [], //一级分类
      subjectTwoList: [], //二级分类
    };
  },

  created() {
    // this.open();
    this.getListTeacher();
    this.initSubjectList();
    if (this.$route.params && this.$route.params.id) {
      this.id = this.$route.params.id;
      this.getCourseInfo();
    }
  },
  watch: {
    // 监听，点击课程添加时执行
    $route(to,from){
      this.courseInfo={}
      this.courseInfo.description=""
      this.courseInfo.cover="https://onlineeducate.oss-cn-beijing.aliyuncs.com/2021/09/10/5.jpg"
    }
  },
  methods: {
    open() {
      this.$alert(
        "此界面内容点击下一步后将无法返回修改，请认真填写",
        "温馨提示",
        {
          confirmButtonText: "确定",
        }
      );
    },

    saveOrUpdate() {
      if (this.courseInfo.id) {
        this.updateCourseInfo();
      } else {
        this.save();
      }
    },

    initSubjectList() {
      subject.getSubjectList().then((response) => {
        this.subjectOneList = response.data.list;
      });
    },

    //查询所有的讲师
    getListTeacher() {
      course.getListTeacher().then((response) => {
        this.teacherList = response.data.list;
      });
    },

    //点击某个一级分类，触发change，显示对应二级分类
    subjectLevelOneChanged(value) {
      //value就是一级分类id值
      //遍历所有的分类，包含一级和二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject = this.subjectOneList[i];
        //判断：所有一级分类id 和 点击一级分类id是否一样
        if (value === oneSubject.id) {
          //从一级分类获取里面所有的二级分类
          this.subjectTwoList = oneSubject.twoSubjectList;
          //把二级分类id值清空
          this.courseInfo.subjectId = "";
        }
      }
    },

    //上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
    },
    //上传之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    save() {
      course.addCourseInfo(this.courseInfo).then((response) => {
        this.$message({
          type: "success",
          message: "添加课程信息成功",
        });
        this.$router.push({
          path: "/course/chapter/" + response.data.courseId,
        });
      });
    },

    getCourseInfo() {
      course.getCourseInfoId(this.id).then((response) => {
        this.courseInfo = response.data.courseInfo;
        // 此处利用查出来一级列表，而无需再次向数据库中递交请求
        for (var i = 0; i < this.subjectOneList.length; i++) {
          if (this.courseInfo.subjectParentId === this.subjectOneList[i].id) {
            this.subjectTwoList = this.subjectOneList[i].twoSubjectList;
          }
        }
      });
    },
    updateCourseInfo() {
      course.updateCourseInfo(this.courseInfo).then((response) => {
        this.$message({
          type: "success",
          message: "更新成功",
        });
        this.$router.push({path:'/course/chapter/'+this.id})
      });
    },
  },
};
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style