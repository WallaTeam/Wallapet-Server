
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `wallapet`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `anuncio`
--

CREATE TABLE IF NOT EXISTS `anuncio` (
`idAnuncio` int(11) NOT NULL,
  `email` varchar(90) NOT NULL,
  `estado` varchar(9) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `descripcion` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tipoIntercambio` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `especie` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rutaImagen` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `titulo` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=536 ;

--
-- Estructura de tabla para la tabla `compraVenta`
--

CREATE TABLE IF NOT EXISTS `compraVenta` (
  `idAnuncio` int(11) NOT NULL,
  `email` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE IF NOT EXISTS `cuenta` (
  `DNI` varchar(9) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `apellidos` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nombre` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `direccion` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `email` varchar(90) NOT NULL,
  `usuario` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `telefono` int(11) NOT NULL,
  `contrasegna` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indices de la tabla `anuncio`
--
ALTER TABLE `anuncio`
 ADD PRIMARY KEY (`idAnuncio`), ADD KEY `email` (`email`);

--
-- Indices de la tabla `compraVenta`
--
ALTER TABLE `compraVenta`
 ADD PRIMARY KEY (`idAnuncio`,`email`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
 ADD PRIMARY KEY (`email`), ADD UNIQUE KEY `DNI` (`DNI`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `anuncio`
--
ALTER TABLE `anuncio`
MODIFY `idAnuncio` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=536;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `anuncio`
--
ALTER TABLE `anuncio`
ADD CONSTRAINT `anuncio_ibfk_1` FOREIGN KEY (`email`) REFERENCES `cuenta` (`email`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
