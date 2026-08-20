<template>
    <h3>產品</h3>
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-primary" @click="openModel('insert')">開啟新增</button>
        </div>
        <div class="col">
            <input type="text" placeholder="產品名稱" v-model="findName" @input="callFind(1)">
        </div>
        <div class="col">
            <ProductSelect
                    :total="total"
                    :options="[2, 3, 4, 5]"
                    v-model="rows"
                    @select-change="callFind">
            </ProductSelect>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col-6" v-show="total>0">
            <Paginate :first-last-button="true"
                    first-button-text="&lt;&lt;" last-button-text="&gt;&gt;"
                    prev-text="&lt;" next-text="&gt;"
                    :page-count="pages"
                    :click-handler="callFind"
                    :initial-page="current"
                    v-model="current">
            </Paginate>
        </div>
    </div> 
    <br>

    <div class="row">
        <div class="col-12 col-sm-6 col-md-6 col-lg-4 col-xl-3 col-xxl-3"
                v-for="item in products" :key="item.id">
            <ProductCard
                    :product="item"
                    @open-update="openModel"
                    @delete="callRemove">
            </ProductCard>
        </div>
    </div>

    <ProductModal
            ref="productModel"
            v-model:product="product"
            :is-show-insert="isShowInsert"
            @insert="callCreate"
            @update="callModify">
    </ProductModal>
</template>

<script setup>
    import ProductSelect from '@/components/ProductSelect.vue';
    import ProductCard from '@/components/ProductCard.vue';
    import ProductModal from '@/components/ProductModal.vue';
    import { ref, onMounted } from 'vue';
    import Paginate from "vuejs-paginate-next";
    import Swal from 'sweetalert2';
    import axiosapi from "@/plugins/axios.js"
    import useUserStore from '@/stores/user.js';

	const userStore = useUserStore();
    const products = ref([]);
    const product = ref({});
    const findName = ref("");
    const isShowInsert = ref(true);
    const productModel = ref(null);

    // 分頁 begin
    const total = ref(10);
    const rows = ref(2);
    const pages = ref(0);
    const start = ref(0);
    const current = ref(0);
    const lastPageRows = ref(0);
    // 分頁 end

    function openModel(action, id) {
        console.log("openModel", action, id);
        if(action==="insert") {
            isShowInsert.value = true;
            product.value = { };
        } else {
            isShowInsert.value = false;
            callFindById(id)
        }
        productModel.value.showModal();
    }
    async function callCreate() {
        Swal.fire({
            title: "執行中......",
            allowOutsideClick: false,
            showConfirmButton: false,
        });        
        if(product.value.id==="") {
            product.value.id = null;
        }
        if(product.value.name==="") {
            product.value.name = null;
        }
        if(product.value.price==="") {
            product.value.price = null;
        }
        if(product.value.make==="") {
            product.value.make = null;
        }
        if(product.value.expire==="") {
            product.value.expire = null;
        }
        const body = product.value;

        try {
            const response = await axiosapi.post("/ajax/pages/products", body, {
                headers: {
                    "Authorization": `Bearer ${userStore.token}`
                }
            });
            if(response.data.success) {
                await Swal.fire({
                    title: response.data.message,
                    icon: "success"
                });
                productModel.value.hideModal();
                callFind(current.value);
            } else {
                Swal.fire({
                    title: response.data.message,
                    icon: "warning"
                });
            }
        } catch (error) {
            console.log("callCreate error:", error);
            Swal.fire({
                title: "新增失敗",
                text: error,
                icon: "error"
            });
        }
    }
    async function callModify() {
        Swal.fire({
            title: "執行中......",
            allowOutsideClick: false,
            showConfirmButton: false,
        });        
        if(product.value.id==="") {
            product.value.id = null;
        }
        if(product.value.name==="") {
            product.value.name = null;
        }
        if(product.value.price==="") {
            product.value.price = null;
        }
        if(product.value.make==="") {
            product.value.make = null;
        }
        if(product.value.expire==="") {
            product.value.expire = null;
        }
        const body = product.value;
        try {
            const response = await axiosapi.put(`/ajax/pages/products/${body.id}`, body, {
                headers: {
                    "Authorization": `Bearer ${userStore.token}`
                }
            });
            if(response.data.success) {
                await Swal.fire({
                    title: response.data.message,
                    icon: "success"
                });
                productModel.value.hideModal();
                callFind(current.value);
            } else {
                Swal.fire({
                    title: response.data.message,
                    icon: "warning"
                });
            }
        } catch (error) {
            console.log("callModify error:", error);
            Swal.fire({
                title: "修改失敗",
                text: error,
                icon: "error"
            });
        }
    }
    async function callRemove(id) {
        const result = await Swal.fire({
            title: "確定要刪除？",
            icon: "question",
            showCancelButton: true,
        });
        if(result.isConfirmed) {
            try {
                const response = await axiosapi.delete(`/ajax/pages/products/${id}`, {
                    headers: {
                        "Authorization": `Bearer ${userStore.token}`
                    }
                });
                if(response.data.success) {
                    await Swal.fire({
                        title: response.data.message,
                        icon: "success"
                    });
                    if(lastPageRows.value===1 && current.value>1) {
                        current.value = current.value - 1;
                    }
                    callFind(current.value);
                } else {
                    Swal.fire({
                        title: response.data.message,
                        icon: "warning"
                    });
                }
            } catch (error) {
                console.log("callRemove error:", error);
                Swal.fire({
                    title: "刪除失敗",
                    text: error,
                    icon: "error"
                });
            }
        }
    }
    function callFindById(id) {
        Swal.fire({
            title: "執行中......",
            allowOutsideClick: false,
            showConfirmButton: false,
        });
        axiosapi.get(`/ajax/pages/products/${id}`, {
            headers: {
                "Authorization": `Bearer ${userStore.token}`
            }
        }).then(function(response) {
            console.log("callFindById success:", response);
            product.value = response.data.list[0];
            
            setTimeout(function() {
                Swal.close();
            }, 500);
        }).catch((error)=> {
            console.log("callFindById error:", error);
            Swal.fire({
                title: "查詢失敗",
                text: error,
                icon: "error"
            });                
        });
    }
    async function callFind(page) {
        if(page) {
            start.value = (page - 1) * rows.value;
            current.value = page;
        } else {
            start.value = 0;
            current.value = 1;
        }

        Swal.fire({
            title: "執行中......",
            allowOutsideClick: false,
            showConfirmButton: false,
        });
        const body = {
            "start": start.value,
            "rows": rows.value,
            "sort": "id",
            "dir": false,
            "name": findName.value
        };
        try {
            const response = await axiosapi.post("/ajax/pages/products/find", body, {
                headers: {
                    "Authorization": `Bearer ${userStore.token}`
                }
            });
            console.log("callFind success:", response);
            total.value = response.data.count;
            products.value = response.data.list;
            pages.value = Math.ceil(total.value / rows.value);
            lastPageRows.value = total.value % rows.value;

            setTimeout(function() {
                Swal.close();
            }, 500);
        } catch (error) {
            console.log("callFind error:", error);
            Swal.fire({
                title: "查詢失敗",
                text: error,
                icon: "error"
            });
        }
    }
    onMounted(() => {
        callFind();
    });
</script>
    
<style>
    
</style>