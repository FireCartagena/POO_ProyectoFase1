-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-11-2024 a las 13:44:22
-- Versión del servidor: 10.6.9-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mediateca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cd`
--

CREATE TABLE `cd` (
  `codc` varchar(8) NOT NULL,
  `codTM` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `artista` varchar(255) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `duracion` int(11) NOT NULL,
  `numeroCanciones` int(11) NOT NULL,
  `unidades` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `cd`
--

INSERT INTO `cd` (`codc`, `codTM`, `titulo`, `artista`, `genero`, `duracion`, `numeroCanciones`, `unidades`) VALUES
('CDA00001', 1, 'Balas Perdidas', 'Morat', 'Pop', 5, 14, 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dvd`
--

CREATE TABLE `dvd` (
  `codd` varchar(8) NOT NULL,
  `codTM` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `director` varchar(255) NOT NULL,
  `duracion` int(11) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `unidades` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `dvd`
--

INSERT INTO `dvd` (`codd`, `codTM`, `titulo`, `director`, `duracion`, `genero`, `unidades`) VALUES
('DVD00001', 1, 'La noche de las luciernagas', 'Fushimoto takada', 150, 'Terror', 125);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro`
--

CREATE TABLE `libro` (
  `codl` varchar(8) NOT NULL,
  `codTM` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `numeroPaginas` int(11) NOT NULL,
  `editorial` varchar(255) NOT NULL,
  `isbn` varchar(50) NOT NULL,
  `anioPublicacion` varchar(4) NOT NULL,
  `unidades` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `libro`
--

INSERT INTO `libro` (`codl`, `codTM`, `titulo`, `autor`, `numeroPaginas`, `editorial`, `isbn`, `anioPublicacion`, `unidades`) VALUES
('LIB00001', 2, 'Harry poter', 'JK Rolling', 500, 'UROXIL', '125699-887', '2024', 50),
('LIB00002', 2, 'Don quijote de la mancha', 'Miguel cervantes', 1424, 'Herrero Hermanos Saturnino Calleja', '9786584956261', '1605', 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `revista`
--

CREATE TABLE `revista` (
  `codr` varchar(8) NOT NULL,
  `codTM` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `editorial` varchar(255) NOT NULL,
  `periocidad` enum('semanal','quincenal','mensual','trimestral','semestral','anual') NOT NULL,
  `fechaPublicacion` date NOT NULL,
  `unidades` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `revista`
--

INSERT INTO `revista` (`codr`, `codTM`, `titulo`, `editorial`, `periocidad`, `fechaPublicacion`, `unidades`) VALUES
('REV00001', 2, 'UFO', 'sueisha', 'semanal', '2024-02-15', 36);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_material`
--

CREATE TABLE `tipo_material` (
  `codTM` int(11) NOT NULL,
  `nombre_tipomaterial` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `tipo_material`
--

INSERT INTO `tipo_material` (`codTM`, `nombre_tipomaterial`) VALUES
(1, 'Material Escrito'),
(2, 'Material Audiovisual');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicaciones`
--

CREATE TABLE `ubicaciones` (
  `id` int(11) NOT NULL,
  `edificio` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `piso` varchar(20) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `estante` varchar(20) COLLATE utf8mb3_unicode_ci NOT NULL,
  `seccion` varchar(20) COLLATE utf8mb3_unicode_ci NOT NULL,
  `descripcion` varchar(200) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `fecha_registro` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Volcado de datos para la tabla `ubicaciones`
--

INSERT INTO `ubicaciones` (`id`, `edificio`, `piso`, `estante`, `seccion`, `descripcion`, `fecha_registro`) VALUES
(1, 'Biblioteca Central', '1', 'A1', 'Informática', 'Sección de libros de programación', '2024-11-23 05:20:54'),
(2, 'Biblioteca Central', '1', 'A2', 'Literatura', 'Sección de literatura clásica', '2024-11-23 05:20:54'),
(3, 'Biblioteca Central', '2', 'B1', 'Matemáticas', 'Sección de matemáticas avanzadas', '2024-11-23 05:20:54'),
(4, 'Hemeroteca', '1', 'H1', 'Revistas', 'Revistas científicas', '2024-11-23 05:20:54'),
(5, 'Archivo', '1', 'T1', 'Tesis', 'Tesis de graduación', '2024-11-23 05:20:54');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tipo_usuario` enum('Administrador','Profesor','Alumno') COLLATE utf8mb3_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `username`, `password`, `tipo_usuario`) VALUES
(1, 'Javier', 'JC2215', 'ABC', 'Administrador');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cd`
--
ALTER TABLE `cd`
  ADD PRIMARY KEY (`codc`),
  ADD KEY `fk_codTM_cd` (`codTM`);

--
-- Indices de la tabla `dvd`
--
ALTER TABLE `dvd`
  ADD PRIMARY KEY (`codd`),
  ADD KEY `fk_codTM_dvd` (`codTM`);

--
-- Indices de la tabla `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`codl`),
  ADD KEY `fk_codTM_libro` (`codTM`);

--
-- Indices de la tabla `revista`
--
ALTER TABLE `revista`
  ADD PRIMARY KEY (`codr`),
  ADD KEY `fk_codTM_revista` (`codTM`);

--
-- Indices de la tabla `tipo_material`
--
ALTER TABLE `tipo_material`
  ADD PRIMARY KEY (`codTM`);

--
-- Indices de la tabla `ubicaciones`
--
ALTER TABLE `ubicaciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tipo_material`
--
ALTER TABLE `tipo_material`
  MODIFY `codTM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `ubicaciones`
--
ALTER TABLE `ubicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cd`
--
ALTER TABLE `cd`
  ADD CONSTRAINT `fk_codTM_cd` FOREIGN KEY (`codTM`) REFERENCES `tipo_material` (`codTM`);

--
-- Filtros para la tabla `dvd`
--
ALTER TABLE `dvd`
  ADD CONSTRAINT `fk_codTM_dvd` FOREIGN KEY (`codTM`) REFERENCES `tipo_material` (`codTM`);

--
-- Filtros para la tabla `libro`
--
ALTER TABLE `libro`
  ADD CONSTRAINT `fk_codTM_libro` FOREIGN KEY (`codTM`) REFERENCES `tipo_material` (`codTM`);

--
-- Filtros para la tabla `revista`
--
ALTER TABLE `revista`
  ADD CONSTRAINT `fk_codTM_revista` FOREIGN KEY (`codTM`) REFERENCES `tipo_material` (`codTM`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
