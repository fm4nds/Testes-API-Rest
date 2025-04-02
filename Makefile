# Makefile para execução de testes automatizados

.PHONY: help install test clean test-status test-report

# Variáveis de configuração
TEST_CLASS := api.TesteStatusAplicacao
REPORT_DIR := target/surefire-reports

help:
	@echo "Comandos disponiveis:"
	@echo "  install    - Instala dependências do projeto"
	@echo "  test       - Executa todos os testes"
	@echo "  test-status - Executa apenas os testes de status da aplicação"
	@echo "  test-report - Gera relatório HTML dos testes"
	@echo "  clean      - Limpa artefatos de construção"
	@echo ""
	@echo "Exemplo:"
	@echo "  make install test"
	@echo "  make test-status"

install:
	@echo "Instalando dependências..."
	mvn clean install

test:
	@echo "Executando todos os testes..."
	mvn test

clean:
	@echo "Limpando ambiente..."
	mvn clean

test-status:
	@echo "Executando testes de status da aplicação..."
	mvn test -Dtest=$(TEST_CLASS)

test-report:
	@echo "Gerando relatório de testes..."
	mvn surefire-report:report
	@echo "Relatório disponível em: $(REPORT_DIR)/index.html"