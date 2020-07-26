#!/bin/bash
# ***********************************************************************
# Copyright: (c) Huawei Technologies Co., Ltd. 2019. All rights reserved.
# script for build
# version: 1.0.0
# change log:
# ***********************************************************************
set -ex
set -o pipefail

#先cd到脚本所在路径，再实现其他处理逻辑，否则该脚本执行会依赖脚本执行的路径
basepath=$(cd `dirname $0`; pwd)
cd $basepath

cd ..

if [[ ${JDK_PATH} != "" ]]; then
	export JAVA_HOME=${JDK_PATH}
	export PATH=${JDK_PATH}/bin:$PATH
fi

#以下脚本开始实现业务具体的编译逻辑







