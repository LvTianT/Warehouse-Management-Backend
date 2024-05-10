<template>
  <div>
    <div style="margin-top: 20px; margin-bottom: 10px;" class="input">
      <div style="margin-left: -28px;margin-right:20px;display: inline-block;">
       <div class="group">
  <svg class="icon" aria-hidden="true" viewBox="0 0 24 25"><g><path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path></g></svg>
  <input  required="" placeholder="请输入姓名" type="search" class="input" v-model="name"  @keyup="loadPost">
</div>
      </div>
      <el-select
        style="width: 250px; margin-right: 20px"
        size="small"
        v-model="sex"
        filterable
        @keyup.enter.native="loadPost"
        placeholder="请选择性别"
      >
        <el-option
          v-for="item in sexs"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
      icon="el-icon-search"
        size="medium"
        style="margin-right: 15px"
        @click="loadPost"
        round
         class="button2"
        type="primary"
        plain
        >查询</el-button
      >
      <div style="float:right;transform: translateX(-50px);">
        <el-button
         icon="el-icon-plus"
        class="button2 button3"
          size="small"
          type="primary"
          style="margin-right: 1px"
          @click="add"
          round
          >新增</el-button
        >
        <el-button
           icon="el-icon-refresh-right"
         class="button2 button4"
          size="small"
          @click="resetParam"
          round
          style="margin-right: 20px"
          >重置</el-button
        >
      </div>
    </div>
    <el-table
      style="margin-left:15px;margin-top: 30px; width: 97%; border-radius:15px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"
      :data="tableData"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      :class="table"
      border
    >
      <el-table-column
        prop="id"
        label="账户编号"
         width="110"
        style="text-align: center"
      >
      </el-table-column>
     <el-table-column prop="no" label="账号" width="100"> </el-table-column>
      <el-table-column prop="name" label="姓名" width="110"> </el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"> </el-table-column>
      <el-table-column prop="age" label="年龄" width="100"> </el-table-column>
      <el-table-column prop="sex" label="性别" width="100">
        <template slot-scope="scope">
          <div
            :type="scope.row.sex === 1 ? 'primary' : 'success'"
            disable-transitions
          >
            {{ scope.row.sex === 1 ? "男" : "女" }}
          </div></template
        >
      </el-table-column>
      <el-table-column prop="roleId" label="身份" width="110">
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.roleId === 0
                ? 'danger'
                : scope.row.roleId === 1
                ? 'danger'
                : 'success'
            "
            size="medium"
            disable-transitions
            >{{
              scope.row.roleId === 0
                ? "高级管理员"
                : scope.row.roleId === 1
                ? "管理员"
                : "用户"
            }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="160"> </el-table-column>
      <el-table-column prop="operate" label="操作" class="cell">
        <template slot-scope="scope">
          <el-button
            class="edit"
            size="medium"
            icon="el-icon-edit"
            @click="mod(scope.row)"
            circle
            style="margin-right: 15px; margin-left: 5px"
          ></el-button>
          <el-popconfirm
            title="确定删除该用户吗？"
            @confirm="del(scope.row.id)"
            type="text"
          >
            <el-button
              class="del"
              slot="reference"
              size="medium"
              type="primary"
              icon="el-icon-delete"
              circle
            ></el-button
          ></el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top: 30px;margin-left:20px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog
      title="新增用户"
      :visible.sync="dialogVisible"
      width="36%"
      center
    >
      <el-form ref="form" :model="form" label-width="70px" :rules="rules"  style="transform: translateX(23px);">
        <el-form-item label="账号" prop="no">
          <el-col :span="18"><el-input v-model="form.no" size="small"></el-input></el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name" size="small"></el-input>
          </el-col>
        </el-form-item>
         <el-form-item label="邮箱" prop="email" size="small">
          <el-col :span="18">
            <el-input v-model="form.email"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password" size="small">
          <el-col :span="18">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age" size="small">
          <el-col :span="18">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1" style="margin-right: 35px">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="18">
            <el-input v-model="form.phone" size="small"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible = false"
          style="transform: translateY(-30px); margin-right: 10px" size="medium"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translateY(-30px);" size="medium"
          >提 交</el-button
        >
      </span>
    </el-dialog>
     <el-dialog
      title="编辑用户"
      :visible.sync="dialogVisible2"
      width="36%"
      center
    >
      <el-form ref="form" :model="form" label-width="70px" :rules="rules"  style="transform: translateX(23px);">
        <el-form-item label="账号" prop="no">
          <el-col :span="18"><el-input v-model="form.no" size="small"></el-input></el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name" size="small"></el-input>
          </el-col>
        </el-form-item>
         <el-form-item label="电话" prop="phone">
          <el-col :span="18">
            <el-input v-model="form.phone" size="small"></el-input>
          </el-col>
        </el-form-item>
           <el-form-item label="邮箱" prop="email">
          <el-col :span="18">
            <el-input v-model="form.email" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="18">
            <el-input v-model="form.password" size="small"></el-input>
          </el-col>
        </el-form-item>
       
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
         size="medium"
          @click="dialogVisible2 = false"
          style="transform:translateY(-30px); margin-right: 10px"
          >取 消</el-button
        >
        <el-button
         size="medium"
          type="primary"
          @click="save"
          style="transform: translateY(-30px);"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "UserManage",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error("年龄输⼊过⼤"));
      } else {
        callback();
      }
    };
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios
        .get(this.$httpUrl + "/user/findByNo?no=" + this.form.no)
        .then((res) => res.data)
        .then((res) => {
          if (res.code !== 200) {
            callback();
          } else {
            callback(new Error("账号已经存在"));
          }
        });
      };
    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: "",
      sex: "",
      sexs: [
        {
          label: "男",
          value: "1",
        },
        {
          label: "女",
          value: "0",
        },
      ],

      dialogVisible: false,
      dialogVisible2:false,
      form: {
        id: "",
        no: "",
        name: "",
        password: "",
        age: "",
        phone: "",
        sex: "0",
        email:"",
        roleId: "2",
      },
      rules: {
        no: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
          { validator: checkDuplicate, trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        password: [
          { required: false, message: "请输入密码", trigger: "blur" },
          { min: 3,message: "长度大于3", trigger: "blur" },
        ],

        age: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          { min: 1, max: 3, message: "长度在 1 到 3 个位", trigger: "blur" },
          {
            pattern: /^([1-9][0-9]*){1,3}$/,
            message: "年龄必须为正整数字",
            trigger: "blur",
          },
          { validator: checkAge, trigger: "blur" },
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id) {
      console.log(id);

      this.$axios
        .get(this.$httpUrl + "/user/del?Id=" + id)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.$message({
              message: "删除成功！",
              type: "success",
            });
            this.loadPost();
          } else {
            this.$message({
              message: "删除失败！",
              type: "error",
            });
          }
        });
    },
    mod(row) {
      console.log(row);
      this.dialogVisible2 = true;
      this.$nextTick(() => {
        this.form.id = row.id;
        this.form.no = row.no;
        this.form.name = row.name;
        this.form.password = "";
        this.form.age = row.age + "";
        this.form.sex = row.sex = "";
        this.form.phone = row.phone;
        this.form.roleId = row.roleId;
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/user/save", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.$message({
              message: "添加成功！",
              type: "success",
            });
            this.dialogVisible = false;
            this.loadPost();
          } else {
            this.$message({
              message: "添加失败！",
              type: "error",
            });
          }
        });
    },

    add() {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/user/update", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.$message({
              message: "修改成功！",
              type: "success",
            });
            this.dialogVisible2 = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: "修改失败！",
              type: "error",
            });
          }
        });
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },

    resetParam() {
      this.name = "";
      this.sex = "";
    },
    loadGet() {
      this.$axios.get(this.$httpUrl + "/user/list").then((res) => {
        console.log(res.data);
      });
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/user/Pagequery", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            sex: this.sex,
            roleId: "2",
          },
        })
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("获取数据失败");
          }
        });
    },
  },
  beforeMount() {
    this.loadPost();
  },
};
</script>

<style scoped>
.input {
  margin: 0 auto;
}
el-table {
  box-sizing: border-box;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-break: break-all;
  line-height: 23px;
  padding-left: 20px;
  padding-right: 10px;
}

.cell {
  padding-left: 10px;
}
.edit {
  border-color: transparent;
  color: #409eff;
  background: 0 0;
  margin-left: 65px;
  padding-left: 3px;
  padding-right: 0;
}
.del {
  border-color: transparent;
     color: #fa5b5b;
     background: #ffffff 0;
}
.button2 {
  display: inline-block;
  transition: all 0.2s ease-in;
  position: relative;
  overflow: hidden;
  z-index: 1;
  color: #090909;
  padding:0.7em 1.2em;
  cursor: pointer;
  font-size: 13px;
  border-radius: 0.5em;
  background: #d7f2df;
  border: 1px solid #fffefe;
  box-shadow: 6px 6px 12px #c5c5c5, -6px -6px 12px #ffffff;
}

.button2:active {
  color: #666;
  box-shadow: inset 4px 4px 12px #c5c5c5, inset -4px -4px 12px #ffffff;
}

.button2:before {
  content: "";
  position: absolute;
  left: 50%;
  transform: translateX(-50%) scaleY(1) scaleX(1.25);
  top: 100%;
  width: 140%;
  height: 180%;
  background-color: rgba(0, 0, 0, 0.05);
  border-radius: 50%;
  display: block;
  transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
  z-index: -1;
}

.button2:after {
  content: "";
  position: absolute;
  left: 55%;
  transform: translateX(-50%) scaleY(1) scaleX(1.45);
  top: 180%;
  width: 160%;
  height: 190%;
  background-color: #95f0ea;
  border-radius: 50%;
  display: block;
  transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
  z-index: -1;
}

.button2:hover {
  color: #ffffff;
  border: 1px solid #e2f5f4;
}

.button2:hover:before {
  top: -35%;
  background-color:  #abcfec;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button2:hover:after {
  top: -45%;
  background-color:  #85b59c;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button3{
  background-color: #bbd1eb
}

.button4{
    background-color: #c9eaf7
}
.form button {
  border: none;
  background: none;
  /* color: #8b8ba7; */
}
.group {
 display: flex;
 line-height: 28px;
 align-items: center;
 position: relative;
 max-width: 190px;
}

.input {
 width: 100%;
 height: 32px;
 line-height: 28px;
 padding: 0 1rem;
 padding-left: 2.5rem;
 border: 2px solid transparent;
 border-radius: 8px;
 outline: none;
 /* background-color: #f3f3f4; */
 color: #0d0c22;
 transition: .3s ease;
}

.input::placeholder {
 color: #9e9ea7;
}

.input:focus, input:hover {
 outline: none;
 border-color: rgba(234,76,137,0.4);
 background-color: #fff;
 box-shadow: 0 0 0 4px  rgb(234 76 137 / 10%);
}

.icon {
 position: absolute;
 left: 1rem;
 /* fill: #9e9ea7; */
 width: 1rem;
 height: 1rem;
}
</style>
