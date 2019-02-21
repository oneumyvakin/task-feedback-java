# Downloader

## URLs list

Add  URLs to urls.txt

## Build

docker build -t downloader:latest  .

## Run

docker run --rm -v $PWD/output:/usr/app/output downloader:latest

## Result

Check result in output/ folder

## Update URLs

Update URLs in urls.txt and rebuild Docker image
