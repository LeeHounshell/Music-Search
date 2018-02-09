#!/bin/sh
curl "https://itunes.apple.com/search?term=tom+waits" > music_test_data.json
curl "http://lyrics.wikia.com/api.php?func=getSong&fmt=json&artist=Tom+Waits&song=i+hope+that+i+don%27t+fall+in+love+with+you" > lyrics_test_data.txt
