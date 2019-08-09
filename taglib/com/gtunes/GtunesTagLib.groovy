package com.gtunes

class GtunesTagLib
{
    static namespace = 'gt';
    static defaultEncodeAs = [taglib:'html'];
    def repeat =
            {
                attributes,
                body ->
                        int n = attributes.int('times')
                        n.times {
                            counter ->
                            out<<body(counter + 1)
                        }

            }
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
}
