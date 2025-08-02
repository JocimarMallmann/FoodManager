CREATE TABLE users
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    login        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    address      VARCHAR(500),
    user_type    VARCHAR(50) DEFAULT 'CUSTOMER',
    enabled      BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE
);

INSERT INTO users (name, email, login, password, address, user_type)
VALUES ('João Silva', 'joao.silva@email.com', 'joao.silva', '$2a$10$N.qSz2sC85XBFEP9tnQ3QOsUB8HcnJcYjM8MBwkEFqJJQPQrE7QxO',
        'Rua das Flores, 123 - São Paulo, SP', 'OWNER'),
       ('Maria Santos', 'maria.santos@email.com', 'maria.santos', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Av. Paulista, 456 - São Paulo, SP', 'CUSTOMER'),
       ('Pedro Oliveira', 'pedro.oliveira@email.com', 'pedro.oliveira', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Rua do Comércio, 789 - Rio de Janeiro, RJ', 'OWNER'),
       ('Ana Costa', 'ana.costa@email.com', 'ana.costa', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Rua das Palmeiras, 321 - Belo Horizonte, MG', 'CUSTOMER'),
       ('Carlos Ferreira', 'carlos.ferreira@email.com', 'carlos.ferreira', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Av. Brasil, 654 - Porto Alegre, RS', 'CUSTOMER'),
       ('Lucia Pereira', 'lucia.pereira@email.com', 'lucia.pereira', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Rua dos Jacarandás, 987 - Curitiba, PR', 'OWNER'),
       ('Roberto Lima', 'roberto.lima@email.com', 'roberto.lima', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Rua Central, 147 - Salvador, BA', 'CUSTOMER'),
       ('Fernanda Alves', 'fernanda.alves@email.com', 'fernanda.alves', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Av. Atlântica, 258 - Recife, PE', 'CUSTOMER'),
       ('Marcos Souza', 'marcos.souza@email.com', 'marcos.souza', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Rua da Paz, 369 - Fortaleza, CE', 'OWNER'),
       ('Juliana Rocha', 'juliana.rocha@email.com', 'juliana.rocha', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
        'Rua das Acácias, 741 - Brasília, DF', 'CUSTOMER');
