<template>
    <div ref="modalRef" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">產品</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table>
                        <tbody>
                            <tr>
                                <td>編號：</td>
                                <td><input type="text" :value="product.id" @input="doinput('id', $event)"></td>
                            </tr>
                            <tr>
                                <td>名稱：</td>
                                <td><input type="text" :value="product.name" @input="doinput('name', $event)"></td>
                            </tr>
                            <tr>
                                <td>價格：</td>
                                <td><input type="text" :value="product.price" @input="doinput('price', $event)"></td>
                            </tr>
                            <tr>
                                <td>製造日期：</td>
                                <td><input type="text" :value="product.make" @input="doinput('make', $event)"></td>
                            </tr>
                            <tr>
                                <td>保存期限：</td>
                                <td><input type="text" :value="product.expire" @input="doinput('expire', $event)"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" @click="emits('insert')" v-show="isShowInsert">新增</button>
                    <button type="button" class="btn btn-primary" @click="emits('update')" v-show="!isShowInsert">修改</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</template>
    
<script setup>
    const props = defineProps(["product", "isShowInsert"]);
    const emits = defineEmits(["update:product", "insert", "update"]);
    function doinput(field, event) {
        emits("update:product", {
            ...props.product,
            [field]: event.target.value
        });
    }

    import bootstrap from "bootstrap/dist/js/bootstrap.bundle.min.js"
    import { ref, onMounted } from "vue";
    const modalRef = ref(null);
    const modal = ref(null);
    onMounted(() => {
        modal.value = new bootstrap.Modal(modalRef.value);
    });
    function showModal() {
        modal.value.show();
    }
    function hideModal() {
        modal.value.hide();
    }
    defineExpose({
        showModal, hideModal
    });
</script>
    
<style>
    
</style>