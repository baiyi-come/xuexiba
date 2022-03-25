<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          :min="0"
          :max="2"
         
        ></el-input-number> 
        <!-- @change="handleChange" -->
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像
        </el-button>
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API + '/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>  
        <!-- :disabled="saveBtnDisabled" -->
        <el-button
        
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacher from "@/api/teacher/teacher";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
export default {
  // 声明
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {},
      //   上传组件框是否显示
      imagecropperShow: false,
      // 上传组件key值
      imagecropperKey: 0,

      BASE_API: process.env.BASE_API,
    };
  },
  created() {
    this.init();
  },
  watch: {
    // 开启监听 解决在修改教师页面点击添加添加教师数据依旧存在的bug
    $route(to, from) {
      this.init();
    },
  },
  methods: {
    init() {
      // 判断路径是否有id值
      if (this.$route.params && this.$route.params.id) {
        // 从路径获取id值
        const id = this.$route.params.id;
        // 调用根据id查询的方法
        this.getTeacherById(id);
      } else {
        // 解决在修改教师页面点击添加添加教师数据依旧存在的bug
        this.teacher = {}; // 清空teacher数据
        this.teacher = {
          sort: 0,
          level: 1,
          avatar:
            "http://aliyun_id_photo_bucket.oss.aliyuncs.com/default_handsome.jpg",
        }; // 重新设置默认值
      }
    },

    saveOrUpdate() {
      if (!this.teacher.id) {
        this.saveTeacher();
      } else {
        this.updateTeacher();
      }
    },
    // 保存教师
    saveTeacher() {
      teacher.saveTeacher(this.teacher).then((response) => {
        this.$message({
          type: "success",
          message: "添加成功",
        });
        this.$router.push({ path: "/teacher/table" });
      });
    },

    // 根据id查询教师信息
    getTeacherById(id) {
      teacher.getTeacherById(id).then((response) => {
        this.teacher = response.data.teacher;
        // console.log(this.teacher);
      });
    },
    updateTeacher() {
      teacher.updateTeacher(this.teacher).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功",
        });
        this.$router.push({ path: "/teacher/table" });
      });
    },

    // 上传头像关闭按钮
    close() {
      this.imagecropperShow = false;

      this.imagecropperKey=this.imagecropperKey+1
    },
    cropSuccess(data) {
      this.imagecropperShow = false;

      // 上传成功后，返回图片地址url
      this.teacher.avatar = data.url;
      this.imagecropperKey=this.imagecropperKey+1
    },
  },
};
</script>
