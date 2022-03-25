<template>
  <div class="app-containeer">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'/static/上传模板.xlsx'">点击下载模版</a>
        </el-tag>
      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/eduservice/subject/saveSubject'"
          name="file"
          accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        >
          <el-button
            slot="trigger"
            @click="selectFile"
            size="small"
            type="primary"
            >选取文件</el-button
          >
          <el-button
            :disabled="importBtnDisabled2"
            :loading="loading"
            style="margin-left: 10px"
            size="small"
            type="success"
            @click="submitUpload"
            >上传到服务器</el-button
          >
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      importBtnDisabled: false, // 按钮是否禁用,
      importBtnDisabled2: true,
      loading: false,
    };
  },
  created() {},
  methods: {
    //点击按钮上传文件到接口里面
    submitUpload() {
      this.importBtnDisabled = true;

      this.loading = true;
      // js: document.getElementById("upload").submit()
      this.$refs.upload.submit();
    },
    // 成功
    fileUploadSuccess(response) {
      console.log(response);
      if (response.code == 20001) {
        this.$message({
          type: "error",
          message: response.message,
        });
      } else {
        this.$message({
          type: "success",
          message: "添加成功",
        });
        this.$router.push({path:'/subject/list'})
      }
    },
    //上传失败
    fileUploadError(response) {
      // this.loading = false
      this.$message({
        type: "error",
        message: "添加课程分类失败",
      });
    },

    /**
     * 上传完文件后打开上传服务器按钮
     */
    selectFile() {
      this.importBtnDisabled2 = false;
    },
  },
};
</script>