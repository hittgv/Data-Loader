import groovy.json.JsonSlurper

def JsonSlurper jsonSlurper = new JsonSlurper()
[
        "bbc-news",
//        "techcrunch",
//        "the-next-web",
//        "associated-press",
        "bbc-sport",
//        "bild",
//        "bloomberg",
//        "business-insider",
//        "cnbc",
//        "cnn",
        "financial-times",
        "national-geographic"
].each {
    println "https://newsapi.org/v1/articles?source=${it}&sortBy=latest&apiKey=15d2a8f668564189a9545baaeae4dfc7"
    def body = new URL("https://newsapi.org/v1/articles?source=${it}&apiKey=15d2a8f668564189a9545baaeae4dfc7").text
    def respBody = jsonSlurper.parseText(body)

    println respBody.articles.url
}

