FILENAME=/home/gorbash/Projects/teach-me/backup/`date +"%m%d%Y%H%M"`_concepts.json
curl https://teach-me-now.herokuapp.com/concepts > $FILENAME
