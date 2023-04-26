# -*-coding: utf-8 -*-
"""
    @Author : panjq
    @E-mail : pan_jinquan@163.com
    @Date   : 2021-02-02 09:57:35
"""
import cv2
import numbers
import random
import PIL.Image as Image
import numpy as np
from torchvision import transforms
from pybaseutils import image_utils


class ResizePadding(object):
    def __init__(self, size=[300, 300]):
        """
        等比例图像resize,保持原始图像内容比，避免失真,短边会0填充
        :param size:
        """
        self.size = tuple(size)

    def __call__(self, image, labels=None):
        is_pil = isinstance(image, Image.Image)
        image = np.asarray(image) if is_pil else image
        height, width, _ = image.shape
        scale = min([self.size[0] / width, self.size[1] / height])
        new_size = [int(width * scale), int(height * scale)]
        pad_w = self.size[0] - new_size[0]
        pad_h = self.size[1] - new_size[1]
        top, bottom = pad_h // 2, pad_h - (pad_h // 2)
        left, right = pad_w // 2, pad_w - (pad_w // 2)
        image = cv2.resize(image, (new_size[0], new_size[1]))
        image = cv2.copyMakeBorder(image, top, bottom, left, right, cv2.BORDER_CONSTANT, None, (0, 0, 0))
        image = Image.fromarray(image) if is_pil else image
        return image, labels


class RandomResize(object):
    """ random resize images"""

    def __init__(self, resize_range, interpolation=Image.BILINEAR):
        """
        :param resize_range: range size range
        :param interpolation:
        """
        self.interpolation = interpolation
        self.resize_range = resize_range

    def __call__(self, img):
        r = int(random.uniform(self.resize_range[0], self.resize_range[1]))
        size = (r, r)
        # print("RandomResize:{}".format(size))
        return transforms.functional.resize(img, size, self.interpolation)

    def __repr__(self):
        interpolation = transforms._pil_interpolation_to_str[self.interpolation]
        return self.__class__.__name__ + '(size={0}, interpolation={1})'.format(self.size, interpolation)


class GaussianBlur(object):
    """Gaussian Blur for image"""

    def __init__(self):
        pass

    def __call__(self, image, ksize=(3, 3), sigmaX=0):
        is_pil = isinstance(image, Image.Image)
        image = np.asarray(image) if is_pil else image
        image = cv2.GaussianBlur(image, ksize, sigmaX)
        image = Image.fromarray(image) if is_pil else image
        return image


class RandomGaussianBlur(object):
    """Random Gaussian Blur for image"""

    def __init__(self, ksize=(1, 1, 1, 3, 3), sigmaX=0, p=0.5):
        """
        :param ksize: Gaussian kernel size. ksize.width and ksize.height can differ but they both must be
                      positive and odd. Or, they can be zero's and then they are computed from sigma.
        :param sigmaX:
        """
        self.ksize = ksize
        self.sigmaX = sigmaX
        self.p = p

    def __call__(self, image):
        if random.random() < self.p:
            r = random.choice(self.ksize)
            is_pil = isinstance(image, Image.Image)
            image = np.asarray(image) if is_pil else image
            image = np.asarray(image)
            image = cv2.GaussianBlur(image, ksize=(r, r), sigmaX=self.sigmaX)
            image = Image.fromarray(image) if is_pil else image
        return image


class RandomMotionBlur(object):
    """
    Random Motion Blur for image运动模糊
    https://www.geeksforgeeks.org/opencv-motion-blur-in-python/
    """

    def __init__(self, degree=5, angle=360, p=0.5):
        """
        :param degree: 运动模糊的程度，最小值为3
        :param angle: 运动模糊的角度,即模糊的方向，默认360度，即随机每个方向模糊
        :param p:
        """
        assert degree >= 3
        self.degree = degree
        self.angle = angle
        self.p = p

    def motion_blur(self, image, degree, angle):
        """
        :param image:
        :param degree: 运动模糊的程度,最小值为3
        :param angle: 运动模糊的角度,即模糊的方向
        :return:
        """
        # print("degree：{},angle：{}".format(degree, angle))
        angle = angle + 45  # np.diag对接矩阵是45度，所以需要补上45度
        # 这里生成任意角度的运动模糊kernel的矩阵， degree越大，模糊程度越高
        mat = cv2.getRotationMatrix2D((degree / 2, degree / 2), angle, 1)
        kernel = np.diag(np.ones(degree))
        # print(degree, angle, mat, kernel)
        kernel = cv2.warpAffine(kernel, mat, (degree, degree))
        kernel = kernel / degree
        image = cv2.filter2D(image, -1, kernel)
        return image

    def __call__(self, image):
        if random.random() < self.p:
            is_pil = isinstance(image, Image.Image)
            image = np.asarray(image) if is_pil else image
            degree = int(random.uniform(3, self.degree + 1))
            angle = int(random.uniform(0, self.angle + 1))
            image = self.motion_blur(image, degree, angle)
            image = Image.fromarray(image) if is_pil else image
        return image


class RandomRotation(object):
    """
    旋转任意角度
    """

    def __init__(self, degrees=5, p=0.5):
        if isinstance(degrees, numbers.Number):
            if degrees < 0:
                raise ValueError("If degrees is a single number, it must be positive.")
            self.degrees = (-degrees, degrees)
        else:
            if len(degrees) != 2:
                raise ValueError("If degrees is a sequence, it must be of len 2.")
            self.degrees = degrees
        self.p = p

    def __call__(self, image):
        if random.random() < self.p:
            is_pil = isinstance(image, Image.Image)
            image = np.asarray(image) if is_pil else image
            angle = random.uniform(self.degrees[0], self.degrees[1])
            h, w, _ = image.shape
            center = (w / 2., h / 2.)
            rot_mat = cv2.getRotationMatrix2D(center, angle, 1.0)
            image = cv2.warpAffine(image, rot_mat, dsize=(w, h), borderMode=cv2.BORDER_CONSTANT)
            image = Image.fromarray(image) if is_pil else image
        return image


class RandomColorJitter(object):
    def __init__(self, p=0.5, brightness=0.5, contrast=0.5, saturation=0.5, hue=0.1):
        """
        :param p:
        :param brightness (float or tuple of float (min, max)): How much to jitter brightness.
            brightness_factor is chosen uniformly from [max(0, 1 - brightness), 1 + brightness]
            or the given [min, max]. Should be non negative numbers.
        :param contrast (float or tuple of float (min, max)): How much to jitter contrast.
            contrast_factor is chosen uniformly from [max(0, 1 - contrast), 1 + contrast]
            or the given [min, max]. Should be non negative numbers.
        :param saturation (float or tuple of float (min, max)): How much to jitter saturation.
            saturation_factor is chosen uniformly from [max(0, 1 - saturation), 1 + saturation]
            or the given [min, max]. Should be non negative numbers.
        :param hue (float or tuple of float (min, max)): How much to jitter hue.
            hue_factor is chosen uniformly from [-hue, hue] or the given [min, max].
            Should have 0<= hue <= 0.5 or -0.5 <= min <= max <= 0.5.(色调建议设置0.1，避免颜色变化过大)
        """
        # from torchvision import transforms
        self.p = p
        self.random_choice = transforms.RandomChoice([
            transforms.ColorJitter(brightness=brightness),
            transforms.ColorJitter(contrast=contrast),
            transforms.ColorJitter(saturation=saturation),
            transforms.ColorJitter(hue=hue),
        ])
        self.color_transforms = transforms.ColorJitter(brightness=brightness,
                                                       contrast=contrast,
                                                       saturation=saturation,
                                                       hue=hue)

    def __call__(self, img):
        if random.random() < self.p:
            # img = self.random_choice(img)
            img = self.color_transforms(img)
        return img


class SwapChannels(object):
    """交换图像颜色通道的顺序"""

    def __init__(self, swaps=[], p=1.0):
        """
        由于输入可能是RGB或者BGR格式的图像，随机交换通道顺序可以避免图像通道顺序的影响
        :param swaps:指定交换的颜色通道顺序，如[2,1,0]
                     如果swaps=[]或None，表示随机交换顺序
        :param p:概率
        """
        self.p = p
        self.swap_list = []
        if not swaps:
            self.swap_list = [[0, 1, 2], [2, 1, 0]]
        else:
            self.swap_list = [swaps]
        self.swap_index = np.arange(len(self.swap_list))

    def __call__(self, image):
        if random.random() < self.p:
            is_pil = isinstance(image, Image.Image)
            image = np.asarray(image) if is_pil else image
            index = np.random.choice(self.swap_index)
            swap = self.swap_list[index]
            image = image[:, :, swap]
            image = Image.fromarray(image) if is_pil else image
        return image


class Crop(object):
    def __init__(self, crop_box):
        """
        :param crop_box: 裁剪类型
        """
        self.crop_box = crop_box

    def __call__(self, image):
        is_pil = isinstance(image, Image.Image)
        image = np.asarray(image) if is_pil else image
        h, w, _ = image.shape
        if isinstance(self.crop_box, list):
            xmin, ymin, xmax, ymax = self.crop_box
        elif self.crop_box == "body":
            xmin, ymin, xmax, ymax = (0, 0, w, int(h / 2))
        else:
            xmin, ymin, xmax, ymax = (0, 0, w, h)
        image = image[ymin:ymax, xmin:xmax]
        image = Image.fromarray(image) if is_pil else image
        return image


class RandomImageFusion(object):
    def __init__(self, p=0.5, bg_dir="bg_image/", is_rgb=True):
        """
        随机图像融合，需要提供RGB+Matte
        :param p: 概率
        :param bg_dir: 背景图库，PS：背景图不能含有目标的对象，避免背景的干扰
        :param is_rgb: 输入的图片是否是RGB图片
        """
        from . import augment_composite
        self.bg_dir = bg_dir
        if self.bg_dir:
            self.fusion = augment_composite.RandomImageComposite(p=p, bg_dir=bg_dir, is_rgb=is_rgb)

    def get_image_mask(self, image, ksize=(3, 3), sigmaX=0):
        mask = image.copy()
        image = 255 - image
        mask = cv2.GaussianBlur(mask, ksize, sigmaX)
        return image, mask

    def __call__(self, image):
        if not self.bg_dir: return image
        is_pil = isinstance(image, Image.Image)
        if is_pil: image = np.asarray(image)
        image, mask = self.get_image_mask(image)
        image = self.fusion(image, mask=mask)
        if is_pil: image = Image.fromarray(image)
        return image


class RandomCenterScale(object):
    """随机中心相对缩小"""

    def __init__(self, scale=(0.5, 1.0), p=0.5):
        """
        :param scale:
        :param p:
        """
        self.scale = scale
        self.p = p

    def __call__(self, image):
        if random.random() < self.p:
            scale = random.uniform(self.scale[0], self.scale[1])
            is_pil = isinstance(image, Image.Image)
            if is_pil: image = np.asarray(image)
            image = image_utils.get_scale_image(image, scale=scale)
            if is_pil: image = Image.fromarray(image)
        return image


class RandomGaussianNoise(object):
    def __init__(self, p=0.5, mean=0.0, sigma=0.05):
        """
        随机添加高斯噪声
        :param p:
        :param mean:均值
        :param sigma:标准差 值越大，噪声越多
        """
        self.p = p
        self.mean = mean
        self.sigma = sigma

    @staticmethod
    def gaussian_noise(image, mean=0.0, sigma=0.05):
        """
        添加高斯噪声
        :param image:原图
        :param mean:均值
        :param sigma:标准差 值越大，噪声越多
        :return:噪声处理后的图片
        """
        image = np.asarray(image / 255, dtype=np.float32)  # 图片灰度标准化
        noise = np.random.normal(mean, sigma, image.shape).astype(dtype=np.float32)  # 产生高斯噪声
        output = image + noise  # 将噪声和图片叠加
        output = np.clip(output, 0, 1)
        output = np.uint8(output * 255)
        return output

    def __call__(self, image):
        if random.random() < self.p:
            is_pil = isinstance(image, Image.Image)
            if is_pil: image = np.asarray(image)
            image = self.gaussian_noise(image, mean=self.mean, sigma=self.sigma)
            if is_pil: image = Image.fromarray(image)
        return image


class RandomNoise(object):
    def __init__(self, vaule=(255,), p=0.5, prob=0.005, thickness=3):
        """
        随机添加噪声值
        :param vaule: 随机添加噪声值
        :param p:
        :param prob: 噪声比例
        :param thickness: 噪声点大小(w,h)
        :param trans_index: list[int],image中需要进行变换的index，
                            trans_index=None或者[],则表示全部都进行变换
        """
        self.p = p
        self.prob = prob
        self.vaule = vaule
        if isinstance(thickness, numbers.Number): thickness = [thickness, thickness]
        self.thickness = thickness

    @staticmethod
    def add_uniform_noise(image: np.ndarray, prob=0.05, vaule=255, thickness=(1, 1)):
        """
        随机生成一个0~1的mask，作为椒盐噪声
        :param image:图像
        :param prob: 噪声比例
        :param vaule: 噪声值
        :return:
        """
        h, w = image.shape[:2]
        shape = [h // thickness[0], w // thickness[1]]
        noise = np.random.uniform(low=0.0, high=1.0, size=shape).astype(dtype=np.float32)  # 产生高斯噪声
        noise = cv2.resize(noise, dsize=(w, h), interpolation=cv2.INTER_NEAREST)
        mask = np.zeros(shape=(h, w), dtype=np.uint8) + vaule
        index = noise > prob
        mask = mask * (~index)
        output = image * index[:, :, np.newaxis] + mask[:, :, np.newaxis]
        output = np.clip(output, 0, 255)
        output = np.uint8(output)
        return output

    def image_uniform_noise(self, image: np.ndarray, prob=0.05, vaule=(255, 0), thickness=(1, 1)):
        """
        随机生成一个0~1的mask，作为椒盐噪声
        :param image:图像
        :param prob: 椒盐噪声噪声比例
        :return:
        """
        for v in vaule:
            image = self.add_uniform_noise(image, prob=prob, vaule=v, thickness=thickness)
        return image

    def __call__(self, image):
        if random.random() < self.p:
            thickness = [int(random.uniform(self.thickness[0], self.thickness[1])),
                         int(random.uniform(self.thickness[0], self.thickness[1]))]
            is_pil = isinstance(image, Image.Image)
            if is_pil: image = np.asarray(image)
            image = self.image_uniform_noise(image, prob=self.prob, vaule=self.vaule, thickness=thickness)
            if is_pil: image = Image.fromarray(image)
        return image


class RandomSaltPepperNoise(object):
    def __init__(self, p=0.5, prob=0.01):
        """
        随机添加椒盐噪声
        :param p:
        :param prob: 噪声比例
        """
        self.p = p
        self.prob = prob

    def salt_pepper_noise(self, image: np.ndarray, prob=0.01):
        """
        添加椒盐噪声，该方法需要遍历每个像素，十分缓慢
        :param image:
        :param prob: 噪声比例
        :return:
        """
        for i in range(image.shape[0]):
            for j in range(image.shape[1]):
                if random.random() < prob:
                    image[i][j] = 0 if random.random() < 0.5 else 255
                else:
                    image[i][j] = image[i][j]
        return image

    def fast_salt_pepper_noise(self, image: np.ndarray, prob=0.02):
        """
        随机生成一个0~1的mask，作为椒盐噪声
        :param image:图像
        :param prob: 椒盐噪声噪声比例
        :return:
        """
        image = self.add_uniform_noise(image, prob * 0.51, vaule=255)
        image = self.add_uniform_noise(image, prob * 0.5, vaule=0)
        return image

    @staticmethod
    def add_uniform_noise(image: np.ndarray, prob=0.05, vaule=255):
        """
        随机生成一个0~1的mask，作为椒盐噪声
        :param image:图像
        :param prob: 噪声比例
        :param vaule: 噪声值
        :return:
        """
        h, w = image.shape[:2]
        noise = np.random.uniform(low=0.0, high=1.0, size=(h, w)).astype(dtype=np.float32)  # 产生高斯噪声
        mask = np.zeros(shape=(h, w), dtype=np.uint8) + vaule
        index = noise > prob
        mask = mask * (~index)
        output = image * index[:, :, np.newaxis] + mask[:, :, np.newaxis]
        output = np.clip(output, 0, 255)
        output = np.uint8(output)
        return output

    def __call__(self, image):
        if random.random() < self.p:
            is_pil = isinstance(image, Image.Image)
            if is_pil: image = np.asarray(image)
            # image = self.salt_pepper_noise(image, prob=self.prob)
            image = self.fast_salt_pepper_noise(image, prob=self.prob)
            if is_pil: image = Image.fromarray(image)
        return image


def demo_for_augment():
    input_size = [192, 256]
    image_path = "test.jpg"
    rgb_mean = [0., 0., 0.]
    rgb_std = [1., 1.0, 1.0]
    image = image_utils.read_image(image_path)
    augment = transforms.Compose([
        transforms.ToPILImage(),
        transforms.Resize([int(128 * input_size[1] / 112), int(128 * input_size[0] / 112)]),
        # RandomMotionBlur(),
        RandomGaussianBlur(),
        transforms.RandomCrop([input_size[1], input_size[0]]),
        transforms.ToTensor(),
        transforms.Normalize(mean=rgb_mean, std=rgb_std),
    ])
    for i in range(1000):
        dst_image = augment(image.copy())
        dst_image = np.array(dst_image, dtype=np.float32)
        dst_image = dst_image.transpose(1, 2, 0)  # 通道由[c,h,w]->[h,w,c]
        print("{},dst_image.shape:{}".format(i, dst_image.shape))
        image_utils.cv_show_image("image", dst_image)
        print("===" * 10)


def demo_for_image():
    image_path = "test.jpg"
    image = image_utils.read_image(image_path)
    image = image_utils.resize_image(image, 256, 192)
    image_utils.cv_show_image("image", image, delay=10)
    augment = RandomMotionBlur()
    for angle in range(360):
        degree = 3
        dst_image = augment.motion_blur(image.copy(), degree, angle)
        print("{},{}，dst_image.shape:{}".format(degree, angle, dst_image.shape))
        image_utils.cv_show_image("dst_image", dst_image)
        print("===" * 10)


if __name__ == "__main__":
    demo_for_augment()
    # demo_for_image()
