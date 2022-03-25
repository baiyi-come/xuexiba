<template>
  <div class="app-containeer">
    <el-input
      v-model="filterText"
      placeholder="关键词搜索"
      style="margin-bottom: 30px"
    />
   <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start">
        <el-button type="text" @click="exportData"
          ><i class="fa fa-plus" /> 导出数据</el-button
        >
      </div>
    </div>
    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />
  </div>
</template>
<script>
import subject from "@/api/subject/subject";
export default {
  data() {
    return {
      filterText: "",
      data2: [], //返回所有分类数据
      defaultProps: {
        children: "twoSubjectList",
        label: "title",
      },
    };
  },
  created() {
    this.getAllSubjectList();
    
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val);
    },
  },

  methods: {
     exportData() {
      window.location.href = "http://localhost:81/eduservice/subject/exportData";
    },
    getAllSubjectList() {
      subject.getSubjectList().then((response) => {
        this.data2 = response.data.list;
      });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1;
    },
  },
};
</script>