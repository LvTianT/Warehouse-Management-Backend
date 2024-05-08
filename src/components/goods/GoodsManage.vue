<template>
  <div>
    <div style="margin-bottom: 5px; display: inline-block">
      <el-input
        v-model="productName"
        placeholder="请输入物品名"
        size="small"
        suffix-icon="el-icon-search"
        style="width: 200px; margin-top: 10px; margin-left: 10px"
        @keyup.enter.native="loadPost"
      ></el-input>
      <el-select
        v-model="warehouseId"
        size="small"
        placeholder="请选择仓库"
        style="margin-left: 5px"
      >
        <el-option
          v-for="item in storageData"
          :key="item.warehouseId"
          :label="item.name"
          :value="item.warehouseId"
        >
        </el-option>
      </el-select>
      <el-select
        size="small"
        v-model="productsType"
        placeholder="请选择分类"
        style="margin-left: 5px"
      >
        <el-option
          v-for="item in goodstypeData"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-button
        icon="el-icon-search"
        style="margin-left: 20px"
        @click="loadPost"
        class="button2"
        >查询</el-button
      >
      <el-button
        icon="el-icon-plus"
        type="primary"
        style="margin-left: 20px"
        @click="add"
        v-if="user.roleId != 2"
        class="button2 button3"
        >新增</el-button
      >
      <el-button
        icon="el-icon-refresh-right"
        type="success"
        size="small"
        style="margin-left: 15px"
        @click="resetParam"
        class="button2 button4"
        >重置</el-button
      >
      <el-button
        icon="el-icon-plus"
        style="margin-left: 75px"
        @click="inGoods"
        v-if="user.roleId != 2"
        class="button2 button3 button5"
        >入库</el-button
      >
      <el-button
        icon="el-icon-minus"
        size="small"
        style="margin-left: 15px"
        @click="outGoods"
        v-if="user.roleId != 2"
        class="button2 button6"
        >出库</el-button
      >
    </div>
    <el-table
      style="
        margin-left: 10px;
        margin-top: 20px;
        width: 98%;
        border-radius: 15px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      :data="tableData"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      :class="table"
      highlight-current-row
      border
      @current-change="selectCurrentChange"
    >
      <el-table-column prop="productId" label="商品编号" width="160">
      </el-table-column>
      <el-table-column prop="productName" label="名称" width="200">
      </el-table-column>
      <el-table-column prop="purchasePrice" label="进价"> </el-table-column>
      <el-table-column prop="salesPrice" label="售价"> </el-table-column>
      <el-table-column prop="supplier" label="供应商"> </el-table-column>
      <el-table-column
        prop="productsType"
        label="分类"
        :formatter="formatProductstype"
      >
      </el-table-column>
      <el-table-column prop="productDescription" label="商品描述">
      </el-table-column>
      <el-table-column
        prop="warehouseId"
        label="仓库"
        :formatter="formatWarehouse"
      >
      </el-table-column>
      <el-table-column prop="operate" label="操作">
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
            title="确定删除此商品吗？"
            @confirm="del(scope.row.productId)"
            style="margin-left: 5px"
          >
            <el-button
              class="del"
              slot="reference"
              size="medium"
              type="primary"
              icon="el-icon-delete"
              circle
            ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="block" style="margin-top: 20px; float: left">
      <span
        class="demonstration"
        style="font-size: 14px; margin-right: 10px; margin-left: 10px"
        >入库时间:</span
      >
      <el-date-picker
        size="small"
        v-model="value1"
        type="datetime"
        placeholder="请选择"
        align="right"
        :picker-options="pickerOptions"
        style="margin-bottom: 20px"
        @change="checkInTime"
      >
      </el-date-picker>
      <span
        class="demonstration"
        style="font-size: 14px; margin-right: 10px; margin-left: 10px"
        >出库时间:</span
      >
      <el-date-picker
        size="small"
        v-model="value2"
        type="datetime"
        placeholder="请选择"
        align="right"
        :picker-options="pickerOptions"
        style="margin-bottom: 10px"
        @change="checkOutTime"
      >
      </el-date-picker>
    </div>
    <el-table
      style="
        margin-left: 10px;
        margin-top: 30px;
        width: 98%;
        border-radius: 15px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      highlight-current-row
      :data="timecheckTable"
      :header-cell-style="{ style1 }"
      border
    >
      <el-table-column prop="productId" label="商品编号" width="160">
      </el-table-column>
      <el-table-column
        prop="productId"
        label="名称"
        width="200"
        :formatter="formatProductsName"
      >
      </el-table-column>
      <el-table-column
        prop="warehouseID"
        label="仓库"
        width="200"
        :formatter="formatInventory"
      >
      </el-table-column>
      <el-table-column prop="quantity" label="库存" @change="isOutOfRange">
      </el-table-column>
      <el-table-column prop="inTime" label="最后入库时间"> </el-table-column>
      <el-table-column prop="outTime" label="最后出库时间"> </el-table-column>
    </el-table>
    <el-pagination
      style="margin-left: 10px; margin-top: 25px"
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
      title="新增商品"
      :visible.sync="centerDialogVisible"
      width="36%"
      center
    >
      <el-form
        ref="form"
        :rules="rules"
        :model="form"
        label-width="80px"
        style="transform: translateX(25px)"
      >
        <el-form-item label="商品名称" prop="productName">
          <el-col :span="18">
            <el-input v-model="form.productName" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="商品编号" prop="productId">
          <el-col :span="18">
            <el-input v-model="form.productId" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="商品描述" prop="productDescription">
          <el-col :span="18">
            <el-input v-model="form.productDescription" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="进价" prop="purchasePrice">
          <el-col :span="18">
            <el-input v-model="form.purchasePrice" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="售价" prop="salesPrice">
          <el-col :span="18">
            <el-input v-model="form.salesPrice" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="供应商" prop="supplier">
          <el-col :span="18">
            <el-input v-model="form.supplier" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="分类" prop="productsType">
          <el-col :span="18">
            <el-select
              size="small"
              v-model="form.productsType"
              placeholder="请选择分类"
              style="width: 286px"
            >
              <el-option
                v-for="item in goodstypeData"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :formatter="formatProductstypeId"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-col :span="18">
            <el-select
              size="small"
              v-model="form.warehouseId"
              placeholder="请选择仓库"
              style="width: 286px"
            >
              <el-option
                v-for="item in storageData"
                :key="item.warehouseId"
                :label="item.name"
                :value="item.warehouseId"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="centerDialogVisible = false"
          style="transform: translateY(-30px); margin-right: 10px"
          size="small"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translateY(-30px)"
          size="small"
          >提 交</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="编辑商品"
      :visible.sync="centerDialogVisible2"
      width="36%"
      center
    >
      <el-form
        ref="form"
        :rules="rules"
        :model="form"
        label-width="80px"
        style="transform: translateX(25px)"
      >
        <el-form-item label="商品名称" prop="productName">
          <el-col :span="18">
            <el-input v-model="form.productName" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="商品编号" prop="productId">
          <el-col :span="18">
            <el-input v-model="form.productId" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="商品描述" prop="productDescription">
          <el-col :span="18">
            <el-input v-model="form.productDescription" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="进价" prop="purchasePrice">
          <el-col :span="18">
            <el-input v-model="form.purchasePrice" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="售价" prop="salesPrice">
          <el-col :span="18">
            <el-input v-model="form.salesPrice" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="供应商" prop="supplier">
          <el-col :span="18">
            <el-input v-model="form.supplier" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="分类" prop="productsType">
          <el-col :span="20">
            <el-select
              size="small"
              v-model="form.productsType"
              placeholder="请选择分类"
              style="width: 286px"
            >
              <el-option
                v-for="item in goodstypeData"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-col :span="18">
            <el-select
              size="small"
              v-model="form.warehouseId"
              placeholder="请选择仓库"
              style="width: 286px"
            >
              <el-option
                v-for="item in storageData"
                :key="item.warehouseId"
                :label="item.name"
                :value="item.warehouseId"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          size="small"
          @click="centerDialogVisible2 = false"
          style="transform: translateY(-30px); margin-right: 10px"
          >取 消</el-button
        >
        <el-button
          size="small"
          type="primary"
          @click="mod2"
          style="transform: translateY(-30px)"
          >保 存</el-button
        >
      </span>
    </el-dialog>
    <el-dialog title="入 库" :visible.sync="inDialogVisible" width="33%" center>
      <el-dialog
        width="75%"
        title="用户选择"
        :visible.sync="innerVisible"
        append-to-body
      >
        <Usertable @doSelectUser="doSelectUser"></Usertable>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>
      </el-dialog>

      <el-form
        ref="form1"
        :rules="rules1"
        :model="form1"
        label-width="80px"
        style="transform: translate(-4px, -20px)"
      >
        <el-form-item label="物品名" prop="productsName">
          <el-col :span="22">
            <el-input
              v-model="form1.productsName"
              readonly
              size="small"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人" prop="instaffname">
          <el-col :span="22">
            <el-input
              v-model="form1.instaffname"
              size="small"
              readonly
              @click.native="selectUser"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="inquantity">
          <el-col :span="22">
            <el-input v-model="form1.inquantity" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="22">
            <el-input
              type="textarea"
              v-model="form1.remark"
              size="small"
            ></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <div style="transform: translate(0px, -50px)">
          <el-button
            @click="inDialogVisible = false"
            size="small"
            style="margin-right: 10px"
            >取 消</el-button
          >
          <el-button type="primary" @click="doInGoods" size="small"
            >确 认</el-button
          >
        </div>
      </span>
    </el-dialog>
    <el-dialog
      title="出 库"
      :visible.sync="inDialogVisible2"
      width="33%"
      center
    >
      <el-dialog
        width="75%"
        title="用户选择"
        :visible.sync="innerVisible"
        append-to-body
      >
        <Usertable @doSelectUser="doSelectUser"></Usertable>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmUser2">确 定</el-button>
        </span>
      </el-dialog>

      <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
        <el-form-item label="物品名" prop="productsName">
          <el-col :span="22">
            <el-input
              v-model="form1.productsName"
              readonly
              size="small"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人" prop="outstaffname">
          <el-col :span="22">
            <el-input
              size="small"
              v-model="form1.outstaffname"
              readonly
              @click.native="selectUser"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="outquantity">
           <el-col :span="22">
          <el-input v-model="form1.outquantity" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="22">
            <el-input
              type="textarea"
              v-model="form1.remark"
              size="small"
            ></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <div style="transform: translate(0px, -30px)">
          <el-button
            @click="inDialogVisible2 = false"
            size="small"
            style="margin-right: 10px"
            >取 消</el-button
          >
          <el-button type="primary" @click="doOutGoods" size="small"
            >确 认</el-button
          >
        </div>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Usertable from "../user/Usertable.vue";
export default {
  productName: "GoodsManage",
  components: { Usertable },
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
      storageData: [],
      goodstypeData: [],
      tableData: [],
      timecheckTable: [],
      alertTable: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      productName: "",
      warehouseId: "",
      productsType: "",
      currentRow: {},
      tempUser: {},
      inDialogVisible: false,
      inDialogVisible2: false,
      innerVisible: false,
      centerDialogVisible: false,
      centerDialogVisible2: false,
      form: {
        productId: "",
        productName: "",
        purchasePrice: "",
        salesPrice: "",
        supplier: "",
        productsType: "",
        warehouseId: "",
        productDescription: "",
      },
      form1: {
        productid: "",
        productsName: "",
        inquantity: "",
        outquantity: "",
        instaff: "",
        outstaff: "",
        remark: "",
        action: "1",
        intime: "",
        outtime: "",
        instaffname: "",
        outstaffname: "",
      },
      rules: {
        productName: [
          { required: true, message: "请输入商品名称", trigger: "blur" },
        ],
        productId: [
          { required: true, message: "请输入商品编号", trigger: "blur" },
        ],
      },
      pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
      value1: "",
      value2: "",
    };
  },
  methods: {
    confirmUser() {
      this.form1.instaffname = this.tempUser.name;
      this.form1.instaff = this.tempUser.id;
      this.innerVisible = false;
    },
    confirmUser2() {
      this.form1.outstaffname = this.tempUser.name;
      this.form1.outstaff = this.tempUser.id;
      this.innerVisible = false;
    },
    inGoods() {
      if (!this.currentRow.productId) {
        alert("请选择记录");
        return;
      }
      this.inDialogVisible = true;
      this.$nextTick(() => {
        this.resetInForm();
      });

      this.form1.productid = this.currentRow.productId;
      this.form1.productsName = this.currentRow.productName;
      this.form1.instaff = this.user.id;
      this.form1.action = "1";
    },
    doInGoods() {
      this.$axios
        .post(this.$httpUrl + "/usertransactions/savein", this.form1)
        // .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.status == 200) {
            this.$message({
              message: "入库成功！",
              type: "success",
            });
            this.inDialogVisible = false;
            this.loadPost();
            this.loadInventory();
            this.resetInForm();
          } else {
            this.$message({
              message: "入库失败！",
              type: "error",
            });
          }
        });
    },
    outGoods() {
      if (!this.currentRow.productId) {
        alert("请选择记录");
        return;
      }
      this.inDialogVisible2 = true;
      this.$nextTick(() => {
        this.resetInForm();
      });
      this.form1.productid = this.currentRow.productId;
      this.form1.productsName = this.currentRow.productName;
      this.form1.instaff = this.user.id;
      this.form1.action = "2";
    },
    doOutGoods() {
      this.$axios
        .post(this.$httpUrl + "/usertransactions/saveout", {
          pageSize:this.pageSize,
          pageNum: this.pageNum,
          param: {
            productid: this.form1.productid,
            productsName: this.form1.productsName,
            inquantity: this.form1.inquantity,
            outquantity:this.form1.outquantity,
            instaff: this.form1.instaff,
            outstaff: this.form1.outstaff,
            remark: this.form1.remark,
            action: this.form1.action,
            intime: this.form1.intime,
            outtime: this.form1.outtime,
            instaffname: this.form1.instaffname,
            outstaffname: this.form1.outstaffname,
          }
        })
        .then((res) => {
          if (res.data.code==400||res.data.msg==="商品库存不足,无法出库") {
            this.$message({
              message: "商品库存不足,无法出库！",
              type: "error",
            });
          } else if (res.status == 200) {
            this.$message({
              message: "出库成功！",
              type: "success",
            });

            this.inDialogVisible2 = false;
            this.loadPost();
            this.loadInventory();
            this.resetInForm();
          } else {
            this.$message({
              message: "出库失败！",
              type: "error",
            });
          }
        });
    },
    selectUser() {
      this.innerVisible = true;
    },
    doSelectUser(val) {
      this.tempUser = val;
    },

    checkInTime(time) {
      const dt = new Date(time);
      const newHour = dt.getHours() + 8;
      dt.setHours(newHour);
      const intime = dt.toJSON();
      const intimedate = intime.slice(0, 10);
      this.$axios
        .post(this.$httpUrl + "/inventory/listInTime?intime=" + intimedate)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.timecheckTable = res.data;
            this.total = res.total;
          } else {
            alert("此刻暂无商品入库信息");
          }
        });
    },
    checkOutTime(time) {
      const dt = new Date(time);
      const newHour = dt.getHours() + 8;
      dt.setHours(newHour);
      const outtime = dt.toJSON();
      const outtimedate = outtime.slice(0, 10);
      this.$axios
        .post(this.$httpUrl + "/inventory/listOutTime?outtime=" + outtimedate)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.timecheckTable = res.data;
            this.total = res.total;
          } else {
            alert("此刻暂无商品出库信息");
          }
        });
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    resetInForm() {
      this.$refs.form1.resetFields();
    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },

    del(productId) {
      console.log(productId);

      this.$axios
        .get(this.$httpUrl + "/Products/del?id=" + productId)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
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
      this.centerDialogVisible2 = true;
      this.$nextTick(() => {
        //赋值到表单
        this.form.productId = row.productId;
        this.form.productName = row.productName;
        this.form.purchasePrice = row.purchasePrice;
        this.form.salesPrice = row.salesPrice;
        this.form.supplier = row.supplier;
        this.form.productsType = row.productsType;
        this.form.warehouseId = row.warehouseId;
        this.form.productDescription = row.productDescription;
      });
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
        this.form.productId = "";
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/Products/save", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "添加成功！",
              type: "success",
            });
            this.centerDialogVisible = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: "添加失败！",
              type: "error",
            });
          }
        });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/Products/update", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "修改成功！",
              type: "success",
            });
            this.centerDialogVisible2 = false;
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
          if (this.form.productId) {
            this.doSave();
          } else {
            this.doMod();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    mod2() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (!this.form.productId) {
            this.doSave();
          } else {
            this.doMod();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    formatWarehouse(row) {
      let temp = this.storageData.find((item) => {
        return item.warehouseId == row.warehouseId;
      });
      return temp ? temp.name : "";
    },
    formatInventory(row) {
      let temp = this.storageData.find((item) => {
        return item.warehouseId == row.warehouseID;
      });
      return temp ? temp.name : "";
    },
    formatProductstype(row) {
      let temp = this.goodstypeData.find((item) => {
        return item.id == row.productsType;
      });
      return temp ? temp.name : "";
    },
    formatProductstypeId(row) {
      let temp = this.goodstypeData.find((item) => {
        return item.name == row.productsType;
      });
      return temp ? temp.id : "";
    },
    formatProductstypeName(id) {
      let temp = this.goodstypeData.find((item) => {
        return item.id == id;
      });
      return temp ? temp.name : "";
    },
    formatProductsName(row) {
      let temp = this.tableData.find((item) => {
        return item.productId == row.productId;
      });
      return temp ? temp.productName : "";
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
      this.productName = "";
      this.warehouseId = "";
      this.productsType = "";
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/Products/listPage", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            productName: this.productName,
            productsType: this.productsType + "",
            warehouseId: this.warehouseId + "",
          },
        })
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadStorage() {
      this.$axios
        .get(this.$httpUrl + "/Warehouses/list")
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.storageData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadGoodstype() {
      this.$axios
        .get(this.$httpUrl + "/goodstype/list")
        .then((res) => res.data)
        .then((res) => {
          // console.log(res);
          if (res.code == 200) {
            this.goodstypeData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadInventory() {
      this.$axios
        .get(this.$httpUrl + "/inventory/list")
        // .then((res)=>console.log(res))
        .then((res) => {
          //   console.log(res);
          if (res.status == 200) {
            this.timecheckTable = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadAlert() {
      this.$axios
        .get(this.$httpUrl + "/inventoryalerts/list")
        // .then((res)=>console.log(res))
        .then((res) => {
          console.log(res);
          if (res.status == 200) {
            this.alertTable = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
  },
  beforeMount() {
    this.loadPost();
    this.loadStorage();
    this.loadGoodstype();
    this.loadInventory();
    this.loadAlert();
  },
};
</script>

<style scoped>

.type {
  color: #000000;
  font-weight: 500;
}
.button2 {
  display: inline-block;
  transition: all 0.2s ease-in;
  position: relative;
  overflow: hidden;
  z-index: 1;
  color: #090909;
  padding: 0.7em 1.2em;
  cursor: pointer;
  font-size: 13px;
  border-radius: 0.5em;
  background: #baf6cb99;
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
  border: 1px solid  #e2f5f4;
}

.button2:hover:before {
  top: -35%;
  background-color: #abcfec;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button2:hover:after {
  top: -45%;
  background-color: #85b59c;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button3 {
  background-color: #bcdafe;
}

.button4 {
  background-color: #c9eaf7;
}

.button5 {
  background-color: #9affd7d0;
}

.button6 {
  background-color: #ffbfbfed;
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
</style>
