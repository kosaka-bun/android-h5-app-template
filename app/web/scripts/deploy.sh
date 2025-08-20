#!/bin/sh

set -e

cd "$(dirname $0)/.."
project_path="$(pwd)"
target_path="$(realpath "$project_path/../src/main/assets/web")"

if [ ! -d node_modules ]; then
  npm install
fi

rm -rf dist
npm run build

rm -rf "$target_path"
mkdir -p "$target_path"

mv -f dist/* "$target_path/"
rm -rf dist
