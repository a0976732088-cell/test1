<template>
	<h3>登入</h3>
	<table>
		<tbody>
			<tr>
				<td>帳號 : </td>
				<td><input type="text" v-model="username"></td>
				<td></td>
			</tr>
			<tr>
				<td>密碼 : </td>
				<td><input type="text" v-model="password"></td>
				<td></td>
			</tr>
			<tr>
				<td> </td>
				<td align="right">
					<button type="button" @click="login">登入</button>
				</td>
			</tr>
		</tbody>
	</table>
</template>
    
<script setup>
	import Swal from 'sweetalert2';
	import { ref } from 'vue';
	import axiosapi from "@/plugins/axios.js"
	import useUserStore from '@/stores/user.js';
	
	const userStore = useUserStore();
	const username = ref("");
	const password = ref("");

	async function login() {
		Swal.fire({
			title: "執行中......",
			allowOutsideClick: false,
			showConfirmButton: false,
		});

		const body = {
			username: username.value,
			password: password.value
		};

		userStore.setEmail("");
		userStore.setToken("");
		try {
			const response = await axiosapi.post("/ajax/secure/login", body);
			if(response.data.success) {
				userStore.setEmail(response.data.email);
				userStore.setToken(response.data.token);
				await Swal.fire({
					title: response.data.message,
					icon: "success"
				});

				window.location.href = "/";
			} else {
				Swal.fire({
					title: response.data.message,
					icon: "warning"
				});
			}
		} catch (error) {
			console.log("error", error);
			Swal.fire({
				title: "登入失敗",
				text: error,
				icon: "error"
			});
		}
	}
</script>
    
<style>
    
</style>