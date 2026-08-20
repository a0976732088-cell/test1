import { defineStore } from "pinia";
import { ref } from "vue";

const useUserStore = defineStore("user", () => {
    const email = ref("");
    const token = ref("");
    function setEmail(value) {
        email.value = value;
    }
    function setToken(value) {
        token.value = value;
    }
    return {
        email,
        token,
        setEmail,
        setToken,
    };
}, {
    persist: {
        storage: sessionStorage
    }
});

export default useUserStore;