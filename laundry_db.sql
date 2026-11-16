-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 12, 2026 at 05:27 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laundry_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_admin`
--

CREATE TABLE `tb_admin` (
  `id_pegawai` varchar(45) NOT NULL,
  `nama_pegawai` varchar(100) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_admin`
--

INSERT INTO `tb_admin` (`id_pegawai`, `nama_pegawai`, `jabatan`, `password`) VALUES
('A001', 'Hans', 'Admin', 'admin123'),
('A002', 'Celmens', 'Admin', 'admin123'),
('A003', 'Kenji', 'Admin', 'admin123'),
('A004', 'yeza', 'Admin', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `tb_layanan`
--

CREATE TABLE `tb_layanan` (
  `id_layanan` varchar(10) NOT NULL,
  `nama_layanan` varchar(100) NOT NULL,
  `harga` double NOT NULL,
  `jenis_satuan` varchar(20) NOT NULL,
  `estimasi_waktu` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_layanan`
--

INSERT INTO `tb_layanan` (`id_layanan`, `nama_layanan`, `harga`, `jenis_satuan`, `estimasi_waktu`) VALUES
('LYN001', 'Cuci Komplit Reguler', 7000, 'Kg', 48),
('LYN002', 'Cuci Komplit Kilat', 12000, 'Kg', 6),
('LYN003', 'Cuci Kering Lipat', 5000, 'Kg', 24),
('LYN004', 'Setrika Saja', 4500, 'Kg', 24),
('LYN005', 'Cuci Bed Cover Besar', 25000, 'Pcs', 72),
('LYN006', 'Cuci Sepatu / Tas', 30000, 'Pcs', 48);

-- --------------------------------------------------------

--
-- Table structure for table `tb_pesanan`
--

CREATE TABLE `tb_pesanan` (
  `id_pesanan` varchar(20) NOT NULL,
  `id_pelanggan` int NOT NULL,
  `id_layanan` varchar(10) NOT NULL,
  `berat` double NOT NULL,
  `biaya_tambahan` double DEFAULT '0',
  `total_biaya` double NOT NULL,
  `status_pesanan` enum('Diterima','Dicuci','Disetrika','Siap Diambil','Sudah Diambil','Dibatalkan') DEFAULT 'Diterima',
  `status_pembayaran` enum('Belum Dibayar','Dibayar Sebagian','Lunas') DEFAULT 'Belum Dibayar',
  `tgl_masuk` datetime NOT NULL,
  `estimasi_selesai` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_pesanan`
--

INSERT INTO `tb_pesanan` (`id_pesanan`, `id_pelanggan`, `id_layanan`, `berat`, `biaya_tambahan`, `total_biaya`, `status_pesanan`, `status_pembayaran`, `tgl_masuk`, `estimasi_selesai`) VALUES
('LP001', 1, 'LYN001', 3.5, 0, 24500, 'Sudah Diambil', 'Lunas', '2026-11-08 09:00:00', '2026-11-10 09:00:00'),
('LP002', 2, 'LYN002', 2, 5000, 29000, 'Siap Diambil', 'Lunas', '2026-11-11 10:15:00', '2026-11-11 16:15:00'),
('LP003', 1, 'LYN005', 1, 0, 25000, 'Disetrika', 'Dibayar Sebagian', '2026-11-10 14:30:00', '2026-11-13 14:30:00'),
('LP004', 3, 'LYN003', 4.2, 0, 21000, 'Dicuci', 'Belum Dibayar', '2026-11-12 08:20:00', '2026-11-13 08:20:00'),
('LP005', 4, 'LYN004', 5, 3000, 25500, 'Diterima', 'Belum Dibayar', '2026-11-12 10:00:00', '2026-11-13 10:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `id_pelanggan` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `alamat` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id_pelanggan`, `nama`, `no_hp`, `alamat`) VALUES
(1, 'Andi Pratama', '081234567890', 'Jl. Diponegoro No. 15, Salatiga'),
(2, 'Rina Wijaya', '085678901234', 'Jl. Kemiri Raya No. 8, Salatiga'),
(3, 'Doni Saputra', '089876543210', 'Perum Asabri Blok C4, Salatiga'),
(4, 'Maya Sari', '081398765432', 'Jl. Blotongan No. 22, Salatiga');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`id_pegawai`);

--
-- Indexes for table `tb_layanan`
--
ALTER TABLE `tb_layanan`
  ADD PRIMARY KEY (`id_layanan`);

--
-- Indexes for table `tb_pesanan`
--
ALTER TABLE `tb_pesanan`
  ADD PRIMARY KEY (`id_pesanan`),
  ADD KEY `id_pelanggan` (`id_pelanggan`),
  ADD KEY `fk_layanan_pesanan` (`id_layanan`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `id_pelanggan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_pesanan`
--
ALTER TABLE `tb_pesanan`
  ADD CONSTRAINT `fk_layanan_pesanan` FOREIGN KEY (`id_layanan`) REFERENCES `tb_layanan` (`id_layanan`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_pesanan_ibfk_1` FOREIGN KEY (`id_pelanggan`) REFERENCES `tb_user` (`id_pelanggan`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
