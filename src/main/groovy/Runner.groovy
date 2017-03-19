import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

def jsonSlurper = new JsonSlurper()

def urls = new File('training-data.csv').readLines()

def writeToFile = { String filename, jsonObj ->

    String json = JsonOutput.toJson(jsonObj)

    new File(filename).write(json)
}

def results = [:]

urls.each {
    def http = new HTTPBuilder('https://boiling-river-48644.herokuapp.com/')
    def postBody = [url: it]

    println "starting $it"

    http.post(path: '/train', body: postBody, requestContentType: ContentType.JSON) { resp, json ->
        def body = jsonSlurper.parseText(json.toString())[0]

        results.put(it, body)

        println JsonOutput.toJson([(it): body])

        if (results.size() == urls.size()) {
            writeToFile('output.json', results)
        }
    }
}

//[{"badOutLinks":13,"goodOutLinks":224,"redirectOutLinks":31,"url":"https:\/\/www.theguardian.com\/media\/2016\/dec\/18\/what-is-fake-news-pizzagate","cssResponseBytes":"531665","htmlResponseBytes":"580680","imageResponseBytes":"288623","javascriptResponseBytes":"1950560","numberCssResources":2,"numberHosts":43,"numberJsResources":40,"numberOfAds":2,"numberResources":124,"numberStaticResources":55,"otherResponseBytes":"12504","textResponseBytes":"124833","totalRequestBytes":"38591","bounceRate":"60.40%","dailyPageViewPerVisitor":"2.37","dailyTimeOnSite":"3:37","globalRank":"164"}]