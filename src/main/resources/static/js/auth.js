function getToken() {
    return localStorage.getItem('token');
}

function getUserId() {
    const id = localStorage.getItem('userId');
    // Nếu dính chuỗi 'undefined', 'null' hoặc trống thì trả về null thay vì 'guest' để tránh lỗi ép kiểu Long ở Backend
    if (!id || id === 'undefined' || id === 'null' || id === 'guest') {
        return null;
    }
    return id;
}

function getUsername() {
    return localStorage.getItem('username') || 'Người dùng SE';
}

function saveAuth(data) {
    localStorage.setItem('token', data.token || 'mock-token-if-none');
    // Fix an toàn: Hỗ trợ cả data.id và data.userId trả về từ Backend
    localStorage.setItem('userId', data.id || data.userId);
    localStorage.setItem('username', data.username);
}

function logout() {
    localStorage.clear();
    window.location.href = '/login';
}

function checkAuth() {
    if (!getToken() || !getUserId()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}

// Hàm gọi API chuẩn bọc Token Authorization bảo mật
async function api(url, options = {}) {
    const headers = options.headers || {};
    headers['Content-Type'] = 'application/json';
    
    if (getToken()) {
        headers['Authorization'] = 'Bearer ' + getToken();
    }

    try {
        const res = await fetch(url, { ...options, headers });
        if (res.status === 401) {
            logout();
            return null;
        }
        return res;
    } catch (err) {
        console.error("API Error: ", err);
        throw err;
    }
}

// Tự động đồng bộ lên Database khi có mạng lại
window.addEventListener('online', async () => {
    const userId = getUserId();
    if (!userId) return;

    const localTasks = JSON.parse(localStorage.getItem('local_tasks_' + userId)) || [];
    
    if (localTasks.length > 0) {
        try {
            // Gửi mảng task local lên REST Controller xử lý bulk
            const res = await api('/api/tasks/' + userId + '/bulk', {
                method: 'PUT',
                body: JSON.stringify(localTasks)
            });
            if (res && res.ok) {
                console.log("🔄 Đồng bộ dữ liệu thành công!");
            }
        } catch (e) {
            console.error("Lỗi đồng bộ online:", e);
        }
    }
});

window.addEventListener('offline', () => {
    alert("⚠️ Chế độ Offline đã kích hoạt. Dữ liệu tạm thời lưu tại trình duyệt!");
});