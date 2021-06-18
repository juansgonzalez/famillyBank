INSERT INTO usuario (nombre_completo, nombre_corto, image ,username, password,resetear_password) values
    ('Usuario Test Banco','Test', 'https://www.nicepng.com/png/detail/202-2022264_usuario-annimo-usuario-annimo-user-icon-png-transparent.png', 'usuario', '$2a$11$TckEYxY/0DPf6OfpQhXKP./hl45UlgXYs0jQFsZZCwBEjUCo7bUKy',0),
    ('Usuario Test Secundario','Test2', 'https://images.vexels.com/media/users/3/200284/isolated/preview/e4ad029ae363094c14e2f050c3009bb6-cabeza-calva-de-avatar-an-oacute-nimo-hombre-by-vexels.png', 'usuario2', '$2a$11$TckEYxY/0DPf6OfpQhXKP./hl45UlgXYs0jQFsZZCwBEjUCo7bUKy',0)
    ;

INSERT INTO cuenta (iban, saldo,usuario_principal) values
    ('ES00 0000 2000 00 0000000000', 3000.0,1)
    ('ES00 0000 2000 00 0000000001', 3025.85,2)
    ;

INSERT INTO categoria (nombre) values
    ('Ocio'),
    ('Restaurantes'),
    ('Compras Internet'),
    ('Viajes'),
    ('SuperMercados'),
    ('Otros')
    ;

INSERT INTO tarjeta (numero,cuenta_id) VALUES
    ("4586847915658147", 1),
    ("4586847915685329", 2)
	;

INSERT INTO movimiento (cantidad, tipo, concepto, saldo_actual, usuario_id, categoria_id, cuenta_id, tarjeta_id, fecha) values
    (35.04, 1, 'Movimiento test 1', 3000-35.04, 1 , 6, 1, 1, '2021-06-09'),
    (20.04, 1, 'Movimiento test 2', 3000-35.04-20.04, 2 , 3, 1, 1, '2021-06-02'),
    (2.87, 1, 'Movimiento test 1 Cuenta 2', 3025.85-2.87, 2 , 3, 2, 2, '2021-06-02')
    ;

-- actualizo saldo de las cuentas según los movimientos realizados
UPDATE cuenta SET saldo = 3000-35.04-20.04 WHERE id=1;
UPDATE cuenta SET saldo = 3025.85-2.87 WHERE id=2;


-- relaciones usuario_cuenta
INSERT INTO usuario_cuenta (saldo_en_cuenta, usuario_id, cuenta_id) values
    (3000-35.04-20.04-50.0,1, 1),
    (50.00,2, 1),
    (3025.85-2.87,2, 2)
    ;