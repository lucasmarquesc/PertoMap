# PertoMap

Aplicativo Android desenvolvido como projeto acadêmico para a disciplina **Sistemas de Informações Geográficas na Web**, do **Curso de Especialização em Desenvolvimento Web e Mobile**.

O PertoMap permite que usuários encontrem pontos de interesse próximos à sua localização atual, organizados por categorias genéricas, com visualização em mapa e cálculo de distância até o destino selecionado.

---

## 📌 Funcionalidades

- Obtenção da localização atual do usuário utilizando serviços de GPS do Android
- Exibição de categorias genéricas de locais em formato de grade
- Busca de pontos de interesse próximos com base na categoria selecionada
- Visualização dos locais em um mapa interativo
- Cálculo da distância entre a posição atual do usuário e um ponto selecionado
- Interface simples e intuitiva, adequada para fins educacionais

---

## 🗺️ Tecnologias Utilizadas

- **Java** para desenvolvimento Android
- **Google Maps SDK** para visualização cartográfica
- **FusedLocationProviderClient** para obtenção da localização do usuário
- **Geoapify Places API** para acesso a dados geoespaciais
- **Volley** para requisições HTTP
- **Material Design** para interface do usuário

---

## 🌐 API Utilizada

O aplicativo utiliza a **Geoapify Places API** para obter informações sobre pontos de interesse próximos à localização do usuário.

Principais parâmetros utilizados nas requisições:

- `categories`: define o tipo de local a ser pesquisado
- `filter`: delimita a área espacial da busca (filtro circular com raio em metros)
- `apiKey`: autenticação do acesso à API

Documentação oficial:  
https://www.geoapify.com/places-api

---

## 📱 Estrutura de Telas

O aplicativo é composto por três telas principais:

1. **Tela Inicial**  
   Apresenta a descrição do aplicativo e um botão para iniciar a navegação.

2. **Tela de Categorias**  
   Exibe categorias genéricas de locais em formato de grade, permitindo a seleção pelo usuário.

3. **Tela de Mapa**  
   Mostra os pontos de interesse no mapa de acordo com a categoria escolhida e permite calcular a distância até um local selecionado.

---

## 🔑 Configuração do Projeto

### Pré-requisitos

- Android Studio instalado
- Conta no Google Cloud com API do Google Maps habilitada
- Chave de acesso da Geoapify Places API

### Configuração das Chaves

1. Insira sua chave do Google Maps no arquivo: res/values/google_maps_api.xml


2. Substitua `API_KEY` pela sua chave da Geoapify Places API no código de requisição HTTP.

---

## 🎓 Contexto Acadêmico

Este projeto foi desenvolvido como parte da avaliação da disciplina **Sistemas de Informações Geográficas na Web**, ministrada pelo professor **Jose Rui Castro de Sousa**, no Curso de Especialização em Desenvolvimento Web e Mobile.

O objetivo principal é demonstrar, de forma prática, a aplicação de conceitos de Sistemas de Informação Geográfica no desenvolvimento de aplicações móveis.

---

## 📎 Repositório

Código-fonte disponível em:  
https://github.com/lucasmarquesc/PertoMap

---

## 📄 Licença

Este projeto é de uso acadêmico e educacional.



