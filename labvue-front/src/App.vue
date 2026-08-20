<template>
  <Navbar></Navbar>
  <div>
    <RouterView></RouterView>
  </div>
</template>
  
<script setup>
  import { RouterView } from 'vue-router';
  import Navbar from './views/Navigationbar.vue';
  
</script>
  
<style>
  
</style>
