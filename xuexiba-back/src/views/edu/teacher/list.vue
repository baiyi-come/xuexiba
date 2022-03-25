<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名" />
      </el-form-item>

      <el-form-item>
        <el-select
          v-model="teacherQuery.level"
          clearable
          placeholder="讲师头衔"
        >
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >修改</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeTeacherById(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <el-pagination
      background
      :page-size="limit"
      :current-page="page"
      style="text-align: center"
      layout="total,prev, pager, next,jumper"
      prev-text="上一页"
      next-text="下一页"
      :total="total"
      @current-change="getList"
    >
    </el-pagination>
  </div>
</template>
<script>
import teacher from "@/api/teacher/teacher";

export default {
  data() {
    // 定义变量和初始值
    return {
      page: 1, // 默认页
      limit: 8, // 每页显示几条数据
      total: 0, // 总记录数
      teacherQuery: {}, // 查询条件
      list: null, // 查询结果返回集合
    };
  },
  created() {
    // 页面渲染之前
    this.getList();
  },
  methods: {
    // 调用方法
    getList(page = 1) {
      this.page = page;
      teacher
        .getTeacherPage(this.page, this.limit, this.teacherQuery)
        .then((response) => {
          // 请求成功s
          this.list = response.data.records;
          this.total = response.data.total;
          // console.log(response);
          // console.log(this.list);
          // console.log(this.total);
        });
    },

    resetData() {
      // 清空数据
      this.teacherQuery = {};
      // 查询所有数据
      this.getList();
    },
    // 删除教师
    removeTeacherById(id) {
      this.$confirm("此操作将永久删除该教师信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        teacher.removeTeacherById(id).then((response) => {
          // 删除成功后，刷新
          this.getList();
        });
        this.$message({
          type: "success",
          message: "删除成功!",
        });
      });
    },
  },
};
</script>


