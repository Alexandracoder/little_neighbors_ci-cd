#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

TIMEOUT=15
STRICT=0
HOST=""
PORT=""
COMMAND=""

echoerr() { echo "$@" 1>&2; }

usage()
{
    cat << USAGE >&2
Usage:
    $0 host:port [-t timeout] [--strict] [-- command args]
Example:
    $0 google.com:80 -- echo "connected"
USAGE
    exit 1
}

wait_for()
{
    local host=$1
    local port=$2
    local timeout=$3
    local start_ts=$(date +%s)
    while :
    do
        (echo > /dev/tcp/$host/$port) >/dev/null 2>&1
        result=$?
        if [ $result -eq 0 ]; then
            return 0
        fi
        now_ts=$(date +%s)
        if [ $((now_ts - start_ts)) -ge $timeout ]; then
            return 1
        fi
        sleep 1
    done
}

# Parse arguments
while [ $# -gt 0 ]
do
    case "$1" in
        --timeout=*) TIMEOUT="${1#*=}" ;;
        --strict) STRICT=1 ;;
        --) shift; COMMAND="$@" ; break ;;
        *) 
            if [[ "$1" == *:* ]]; then
                HOST=$(echo $1 | cut -d: -f1)
                PORT=$(echo $1 | cut -d: -f2)
            else
                usage
            fi
            ;;
    esac
    shift
done

if [ -z "$HOST" ] || [ -z "$PORT" ]; then
    usage
fi

echo "Waiting for $HOST:$PORT..."
wait_for $HOST $PORT $TIMEOUT
result=$?

if [ $result -ne 0 ]; then
    echo "Timeout occurred after $TIMEOUT seconds waiting for $HOST:$PORT"
    if [ $STRICT -eq 1 ]; then
        exit 1
    fi
fi

if [ ! -z "$COMMAND" ]; then
    exec $COMMAND
fi

