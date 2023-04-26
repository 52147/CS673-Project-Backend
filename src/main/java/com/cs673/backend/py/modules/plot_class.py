# -*-coding: utf-8 -*-
"""
    @Author : PKing
    @E-mail : 390737991@qq.com
    @Date   : 2023-01-13 17:00:05
    @Brief  :
"""

from pybaseutils import plot_utils, image_utils, file_utils

if __name__ == '__main__':
    file = "../data/province_count.json"
    data: dict = file_utils.read_json_data(file)
    y = list(data.values())
    x = list(data.keys())
    plot_utils.plot_bar_text(x, y)
    plot_utils.plot_bar_text(x, y)
