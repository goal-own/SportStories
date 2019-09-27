import vk_api


class VkApiManager:
    api = None

    def __init__(self, user, password):
        session = vk_api.VkApi(user, password)
        session.auth()
        self.api = session.get_api()

    def get_friends(self, fields):
        """
        :param fields:
             must be instance of list
             available fields:
                nickname, domain, sex, bdate, city, country, timezone,
                photo_50, photo_100, photo_200_orig, has_mobile, contacts,
                education, online, relation, last_seen, status, can_write_private_message,
                an_see_all_posts, can_post, universities
        :return:
            list of friends with defined fields
        """
        if not isinstance(fields, list):
            raise Exception("fields must be instance of list, check get_friends.__doc__ for more information")

        return self.api.friends.get(fields=fields).get('items')
