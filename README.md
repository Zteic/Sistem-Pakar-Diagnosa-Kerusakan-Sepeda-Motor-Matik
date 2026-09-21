# 🛵 Sistem Pakar Diagnosa Kerusakan Sepeda Motor Matik

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)](https://netbeans.apache.org/)
[![Method](https://img.shields.io/badge/Method-Forward%20Chaining-brightgreen?style=for-the-badge)](#-metode-penalaran)

Aplikasi desktop **Sistem Pakar** berbasis Java Swing untuk membantu pemilik kendaraan sepeda motor matik mendiagnosa indikasi awal kerusakan berdasarkan gejala teknis maupun fisik yang dirasakan, lengkap dengan rekomendasi solusi penanganannya.

---

## 📌 Fitur Utama

- 🔐 **Sistem Autentikasi / Login:** Halaman masuk aman menggunakan validasi pengguna.
- 📋 **Kuesioner Interaktif:** Antarmuka pemilihan gejala kerusakan yang intuitif berbasis kuesioner `Ya / Tidak`.
- 🧠 **Mesin Inferensi Forward Chaining:** Evaluasi aturan fakta secara dinamis untuk menentukan jenis kerusakan.
- 📊 **Dialog Hasil Diagnosa Modern:** Menampilkan rincian gejala terpilih, kesimpulan kerusakan, dan petunjuk solusi penanganan.
- 🎨 **Antarmuka UI/UX Modern:** Desain antarmuka kustom menggunakan komponen Java Swing.

---

## 🧠 Metode Penalaran & Basis Pengetahuan

Aplikasi ini menggunakan metode **Forward Chaining** (*data-driven*), yaitu penalaran maju yang dimulai dari pengumpulan fakta-fakta gejala ($G01 - G10$) hingga menghasilkan keputusan jenis kerusakan ($P01 - P05$).

### 1. Daftar Kerusakan ($P$)
| Kode | Jenis Kerusakan / Komponen | Solusi Penanganan |
| :---: | :--- | :--- |
| **P01** | Masalah Busi / Pengapian | Bersihkan bukaan elektroda busi atau ganti busi baru. |
| **P02** | CVT (Roller / Vanbelt) Aus | Cek dan ganti roller/vanbelt yang aus di bengkel. |
| **P03** | Akumulator / Aki Tekor | Charge ulang tegangan aki atau ganti aki baru. |
| **P04** | Sistem Injeksi / Throttle Body Kotor | Servis dan bersihkan *throttle body* serta injektor. |
| **P05** | Kampas Rem Aus | Ganti kampas rem depan/belakang baru. |

### 2. Aturan Keputusan (*Rules*)
* **RULE 1:** `IF G01 AND G02 AND G03` $\rightarrow$ **P03** (Aki Tekor)
* **RULE 2:** `IF G01 AND G04 AND G07` $\rightarrow$ **P01** (Masalah Busi)
* **RULE 3:** `IF G05 AND G06 AND G08` $\rightarrow$ **P02** (CVT Aus)
* **RULE 4:** `IF G04 AND G07` $\rightarrow$ **P04** (Sistem Injeksi Kotor)
* **RULE 5:** `IF G09 AND G10` $\rightarrow$ **P05** (Kampas Rem Aus)

---

## 🛠️ Teknologi & Tools

- **Bahasa Pemrograman:** Java (JDK 8+)
- **GUI Framework:** Java Swing
- **IDE:** Apache NetBeans IDE / Eclipse / VS Code
- **Build Tool:** Ant / Standard Java Compiler

---

## 🔑 Kredensial Bawaan (Default Login)

Gunakan akun berikut untuk masuk ke dalam aplikasi:

| Username | Password |
| :---: | :---: |
| `admin` | `admin` |

---

## 🚀 Cara Menjalankan Proyek

### Menggunakan Apache NetBeans IDE (Direkomendasikan)
1. *Clone* repositori ini ke komputer lokal Anda:
   ```bash
   git clone [https://github.com/USERNAME_KAMU/Sistem-Pakar-Diagnosa-Kerusakan-Sepeda-Motor-Matik.git](https://github.com/USERNAME_KAMU/Sistem-Pakar-Diagnosa-Kerusakan-Sepeda-Motor-Matik.git)
